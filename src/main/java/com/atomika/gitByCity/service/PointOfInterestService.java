package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.PointOfInterest;
import com.atomika.gitByCity.dto.PointOfInterestRoute;
import com.atomika.gitByCity.dto.ResponseForCreateOrUpdate;
import com.atomika.gitByCity.dto.mapper.PointOfInterestMapper;
import com.atomika.gitByCity.dto.mapper.PointOfInterestRouteMapper;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.PointOfInterestEntity;
import com.atomika.gitByCity.exception.ExistsException;
import com.atomika.gitByCity.exception.NotFoundException;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Slf4j
public class PointOfInterestService {

    private final PointOfInterestRepository pointOfInterestRepository;
    private final PointOfInterestMapper pointOfInterestMapper;
    private final ClientRepository clientRepository;
    private final PointOfInterestRouteRepository pointOfInterestRouteRepository;
    private final PointOfInterestRouteMapper pointOfInterestRouteMapper;
    private final RouteService routeService;

    @Transactional
    public ResponseForCreateOrUpdate create (PointOfInterest pointOfInterest, String clientName) {
        PointOfInterestEntity isExists = pointOfInterestRepository.findPointOfInterestEntityByName(pointOfInterest.getName());
        if (isExists != null) {
            log.error("Точка {} не была добавлена", pointOfInterest.getName());
            throw new ExistsException("Точка с таким названием уже существует");
        }else {
            log.info("Точка {} была успешно сохранена", pointOfInterest.getName());
            pointOfInterest.setClientId(clientRepository.findClientIdByUsername(clientName));
            pointOfInterestMapper.entityToDto(
                    pointOfInterestRepository.save(pointOfInterestMapper.dtoToEntity(pointOfInterest)));
            return ResponseForCreateOrUpdate.builder().message("Точка успешно создана, поздравляю!!!").
                    success(true).build();
        }
    }

    @Transactional
    public ResponseForCreateOrUpdate update (PointOfInterest pointOfInterest) {
        PointOfInterestEntity isExists = pointOfInterestRepository.findPointOfInterestEntityByName(pointOfInterest.getName());
        if (isExists != null && !isExists.getId().equals(pointOfInterest.getId())) {
            log.error("Точка с таким названием {} уже есть в базе данных", pointOfInterest.getName());
            throw new ExistsException("Точка с таким названием уже существует");
        }else {
            log.info("Точка с таким названием {} успешно сохранена", pointOfInterest.getName());
            pointOfInterestMapper.entityToDto(
                    pointOfInterestRepository.save(pointOfInterestMapper.dtoToEntity(pointOfInterest)));
            return ResponseForCreateOrUpdate.builder().message("Точка успешно сохранена, поздравляю!!!").
                    success(true).build();
        }
    }

    @Transactional
    public Long delete (Long id) {
        List<PointOfInterestRoute> list = pointOfInterestRouteMapper.toList(pointOfInterestRouteRepository.findByPointOfInterestId(id));
        List<Long> routeIds = list.stream().map(PointOfInterestRoute::getRouteId).toList();
        pointOfInterestRepository.deleteById(id);
        for (Long routeId : routeIds) {
            routeService.updatePosition(routeId);
        }
        log.info("Точка с id: {} была успешно удалена", id);
        return id;
    }

    @Transactional(readOnly = true)
    public List<PointOfInterest> findAll() {
        log.info("Список точек был успешно получен");
        return pointOfInterestMapper.toList(pointOfInterestRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PointOfInterest findById(Long id) {
        log.info("Точка с id: {} успешно получена", id);
        return pointOfInterestMapper.entityToDto(pointOfInterestRepository.findById(id).orElse(null));
    }

    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<Long> addLike(Long pointId, String clientName) {
        try {
            Thread.sleep(400);
            PointOfInterestEntity pointOfInterestEntity = pointOfInterestRepository.findById(pointId).orElse(null);
            Long clientId = clientRepository.findClientIdByUsername(clientName);
            if (clientId == null) {
                throw new NotFoundException("Пользователь не найден");
            }
            ClientEntity clientEntity = clientRepository.findById(clientId).orElse(null);
            if (pointOfInterestEntity == null) {
                throw new NotFoundException("Маршрут не найден");
            } else {
                boolean isCreator = clientEntity.getLikedPoints().stream().
                        anyMatch(point -> Objects.equals(point.getId(), pointId));
                if (isCreator) {
                    deleteLike(pointOfInterestEntity, clientEntity);
                } else {
                    clientEntity.getLikedPoints().add(pointOfInterestEntity);
                    pointOfInterestEntity.getLikes().add(clientEntity);
                    clientRepository.save(clientEntity);
                }
                List<ClientEntity> list = pointOfInterestEntity.getLikes();
                if (!list.isEmpty()) {
                    return CompletableFuture.completedFuture((long) list.size());
                } else {
                    return null;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Transactional
    public void deleteLike(PointOfInterestEntity point, ClientEntity clientEntity) {
        clientEntity.getLikedPoints().remove(point);
        point.getLikes().remove(clientEntity);
        clientRepository.save(clientEntity);
        log.info("Лайк удален с точки: {}", point.getName());
    }

    public boolean isCreator (String username, long pointOfInterestId) {
        return pointOfInterestRepository.isCreator(username, pointOfInterestId);
    }


    @Async
    public void asyncLog(){
        try {
            Thread.sleep(3000);
            log.info("Ура выполнился асинхронный метод");
        }catch (InterruptedException e) {
            e.getMessage();
        }

    }
}

package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.Comment;
import com.atomika.gitByCity.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

//    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentEntity dtoToEntity (Comment comment);

    Comment entityToDto (CommentEntity commentEntity);

    List<Comment> toList (List<CommentEntity> commentEntityList);

    List<CommentEntity> toListEntity(List<Comment> commentsDto);
}

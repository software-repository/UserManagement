package com.jas.mapper;

import com.jas.dto.UserDTO;
import com.jas.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AutoUserMapper {

    AutoUserMapper MAPPER = Mappers.getMapper(AutoUserMapper.class);

    public UserDTO userEntityToDTO(User user);
    public User userDTOtoEntity(UserDTO userDTO);


}

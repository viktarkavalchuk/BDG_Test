package by.bdg.task.rest.converter;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BasicConverter<Entity, DTO> {

    private final ModelMapper modelMapper = new ModelMapper();

    public DTO convertToDto(Entity entity, Class<DTO> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public Entity convertToEntity(DTO dto, Class<Entity> entityClass) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(dto, entityClass);
    }
}

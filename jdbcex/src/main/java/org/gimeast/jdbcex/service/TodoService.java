package org.gimeast.jdbcex.service;

import lombok.extern.log4j.Log4j2;
import org.gimeast.jdbcex.dao.TodoDao;
import org.gimeast.jdbcex.domain.TodoVo;
import org.gimeast.jdbcex.dto.TodoDto;
import org.gimeast.jdbcex.util.MapperUtil;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public enum TodoService {
    INSTANCE;

    private TodoDao todoDao;
    private ModelMapper modelMapper;

    TodoService() {
        this.todoDao = new TodoDao();
        this.modelMapper = MapperUtil.INSTANCE.get();
    }

    public void register(TodoDto todoDto) throws Exception {
        TodoVo todoVo = modelMapper.map(todoDto, TodoVo.class);

        log.info("todoVo: {}",todoVo);

        todoDao.insert(todoVo);
    }

    public List<TodoDto> listAll() throws Exception {
        List<TodoVo> listVo = todoDao.selectAll();
        List<TodoDto> result = listVo.stream().map(vo ->
                modelMapper.map(vo, TodoDto.class)
        ).collect(Collectors.toList());

        return result;
    }

    public TodoDto get(Long tno) throws Exception {
        TodoVo todoVo = todoDao.selectOne(tno);
        TodoDto result = modelMapper.map(todoVo, TodoDto.class);
        return result;
    }

    public void modify(TodoDto todoDto) throws Exception {
        TodoVo vo = modelMapper.map(todoDto, TodoVo.class);
        todoDao.updateOne(vo);
    }

    public void remove(Long tno) throws Exception {
        todoDao.deleteOne(tno);
    }
}

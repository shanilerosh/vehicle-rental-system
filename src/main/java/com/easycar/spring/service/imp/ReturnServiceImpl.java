package com.easycar.spring.service.imp;

import com.easycar.spring.dto.ReturnDTO;
import com.easycar.spring.entity.Return;
import com.easycar.spring.repo.ReturnRepo;
import com.easycar.spring.service.ReturnService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReturnServiceImpl implements ReturnService {


    @Autowired
    ModelMapper mapper;

    @Autowired
    ReturnRepo returnRepo;

    @Override
    public void addReturn(ReturnDTO returnDTO) {
        returnRepo.save(mapper.map(returnDTO, Return.class));
    }
}

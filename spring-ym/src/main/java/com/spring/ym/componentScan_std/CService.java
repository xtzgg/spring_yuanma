package com.spring.ym.componentScan_std;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CService {

    @Autowired
    private CRepository cRepository;

}

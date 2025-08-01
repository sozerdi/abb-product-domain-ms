package az.abb.template.service.impl;

import az.abb.template.exception.SampleErrorEnum;
import az.abb.template.exception.SampleException;
import az.abb.template.service.SampleErrorService;
import org.springframework.stereotype.Service;

@Service
public class SampleErrorServiceImpl implements SampleErrorService {

    public void throwException() {
        throw new SampleException(SampleErrorEnum.UNAUTHORIZED_ERROR);
    }
}

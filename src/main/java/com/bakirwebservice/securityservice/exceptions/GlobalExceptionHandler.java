package com.bakirwebservice.securityservice.exceptions;

import com.bakirwebservice.securityservice.api.response.BaseResponse;
import com.bakirwebservice.securityservice.model.ErrorCodes;
import com.bakirwebservice.securityservice.model.dto.ErrorCodesDTO;
import com.bakirwebservice.securityservice.rest.service.interfaces.ICacheService;
import com.bakirwebservice.securityservice.rest.service.interfaces.IMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.bakirwebservice.securityservice.constants.ResponseStatus.FAILED_STATUS;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j

public class GlobalExceptionHandler {

    private final IMapperService mapperService;

    private final ICacheService cacheService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        log.error("Error: ",e);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(createFailResponse(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createFailResponse(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleException (NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createFailResponse(e.getMessage()));
    }

    private BaseResponse createFailResponse(String exceptionMessage){
        ErrorCodesDTO errorCodesDTO = findErrorCode(exceptionMessage);
        return new BaseResponse(errorCodesDTO.getId(),errorCodesDTO.getError(),errorCodesDTO.getDescription());
    }

    private ErrorCodesDTO findErrorCode(String errorKey) {
        ErrorCodes errorCodes = cacheService.getErrorCodesList().get(errorKey);
        if (errorCodes == null) {
            errorCodes = new ErrorCodes();
            errorCodes.setId(FAILED_STATUS);
            errorCodes.setError(errorKey);
            errorCodes.setDescription(errorKey);
        }
        return mapperService.map(errorCodes, ErrorCodesDTO.class);
    }
}
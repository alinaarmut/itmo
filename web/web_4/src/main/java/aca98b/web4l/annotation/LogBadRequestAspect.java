package aca98b.web4l.annotation;

import aca98b.web4l.config.ApplicationContextProvider;
import aca98b.web4l.model.entities.BadRequest;
import aca98b.web4l.model.entities.repo.BadRequestRepository;
import aca98b.web4l.model.response.Response;
import aca98b.web4l.processor.LogBadRequestBeanPostProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Aspect
@Component
public class LogBadRequestAspect {
    private final BadRequestRepository badRequestRepository;
    public LogBadRequestAspect(BadRequestRepository badRequestRepository) {
        this.badRequestRepository = badRequestRepository;
    }

    @Around("@annotation(LogBadRequest)")
    public Object handleBadRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object result;
        LogBadRequestBeanPostProcessor beanPostProcessor = getLogBadRequestBeanPostProcessor();

        beanPostProcessor.postProcessBeforeInitialization(joinPoint.getTarget(), methodName);
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logBadRequest(e, methodName);
            throw e;
        }
        beanPostProcessor.postProcessAfterInitialization(joinPoint.getTarget(), methodName);

        if (result instanceof Response) {
            logBadRequest((Response) result, methodName);
        }

        return result;
    }

    private void logBadRequest(Exception exception, String methodName) {
        int statusCode = getStatusCode(exception);
        if (String.valueOf(statusCode).matches("^4\\d{2}$")) {
            BadRequest badRequest = BadRequest.builder()
                    .time(String.valueOf(System.currentTimeMillis()))
                    .status(String.valueOf(statusCode))
                    .statusCode(statusCode)
                    .message(exception.getMessage())
                    .methodName(methodName)
                    .username("undefined")
                    .build();
            badRequestRepository.save(badRequest);
        }
    }

    private void logBadRequest(Response response, String methodName) {
        int statusCode = response.getStatusCode();
        if (response.getStatus().is4xxClientError()) {
            BadRequest badRequest = BadRequest.builder()
                    .time(String.valueOf(response.getTimeStamp()))
                    .status(response.getStatus().getReasonPhrase())
                    .statusCode(statusCode)
                    .message(response.getMessage())
                    .methodName(methodName)
                    .username(response.getUsername())
                    .build();
            badRequestRepository.save(badRequest);
        }
    }

    private int getStatusCode(Exception exception) {
        if (exception instanceof ResponseStatusException responseStatusException) {
            return responseStatusException.getStatusCode().value();
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }

    private LogBadRequestBeanPostProcessor getLogBadRequestBeanPostProcessor() {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(LogBadRequestBeanPostProcessor.class);
    }
}
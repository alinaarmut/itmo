package aca98b.web4l.processor;

import aca98b.web4l.annotation.LogBadRequest;
import aca98b.web4l.annotation.LogBadRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@Slf4j
@Component
public class LogBadRequestBeanPostProcessor implements BeanPostProcessor {
    private final Class<LogBadRequest> logBadRequestClass = LogBadRequest.class;
    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        log.info("{} - `{}` is ready.", bean, beanName);
        if (bean.getClass().isAnnotationPresent(logBadRequestClass)) {
            System.out.println("Bean has `" + logBadRequestClass.getName() + "` annotation.");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, @NotNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(logBadRequestClass)) {
            return Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new LogBadRequestHandler()
            );
        }
        return bean;
    }
}
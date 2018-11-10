package demo.com.transaction;

import demo.com.annotation.MyTransaction;
import demo.com.helper.DataBaseHelper;
import demo.com.intface.MyProxy;
import demo.com.proxy.MyProxyChain;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 事务的切面类
 */
@Slf4j
public class TransactionProxy implements MyProxy {

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(MyProxyChain proxyChain) throws Throwable {

        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method targetMethod = proxyChain.getTargetMethod();
        //判断是否有 事务注解，确定是否做代理
        if (!flag && targetMethod.isAnnotationPresent(MyTransaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DataBaseHelper.beginTransaction();
                if (log.isDebugEnabled()) {
                    log.debug("begin transaction");
                }
                result = proxyChain.doProxyChain();
                DataBaseHelper.commitTransaction();
                if (log.isDebugEnabled()) {
                    log.debug("commit transaction");
                }
            } catch (Exception e) {
                log.warn("rollback transaction");
                DataBaseHelper.rollbackTransaction();
                if (log.isDebugEnabled()) {
                    log.debug("rollback transaction");
                }
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            result = proxyChain.doProxyChain();

        }
        return result;
    }
}

package kata;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DataOpsDispatcherBuilder<T> {
	private Class<T> type;
	private Object data;
	private Object ops;

	public DataOpsDispatcherBuilder<T> type(Class<T> type) {
		this.type = type;
		return this;
	}

    public DataOpsDispatcherBuilder<T> data(Object data) {
        this.data = data;
        return this;
    }

	public DataOpsDispatcherBuilder<T> operations(Object ops) {
		this.ops = ops;
		return this;
	}

    public T toEntity() {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{this.type}, new Dispatcher());
    }

	private class Dispatcher implements InvocationHandler {
		@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return isAccessor(method, args) ? method.invoke(data, args) : method.invoke(ops, args);
		}

		private boolean isAccessor(Method method, Object[] args) {
			boolean setter = namingConvention(method, "set") && one(args);
			boolean getter = namingConvention(method, "get") && no(args) && returnValueOn(method);
			return setter || getter;
		}

		private boolean namingConvention(Method method, String prefix) {
			return method.getName().matches(prefix + "[\\p{javaUpperCase}]{1}.*");
		}

		private boolean one(Object[] args) {
			return args != null && args.length == 1;
		}

		private boolean no(Object[] args) {
			return args == null;
		}

		private boolean returnValueOn(Method method) {
			return !method.getReturnType().getName().equals("void");
		}
	}
}

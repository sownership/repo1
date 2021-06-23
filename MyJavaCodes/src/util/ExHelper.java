package util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public class ExHelper {
	@FunctionalInterface
	static interface IntFunctionWithEx<R, E extends Exception> {
		R apply(int t) throws E;
	}

	static <R, E extends Exception> IntFunction<R> ifWrapper(IntFunctionWithEx<R, E> fe) {
		return arg -> {
			try {
				return fe.apply(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@FunctionalInterface
	static interface FunctionWithEx<T, R, E extends Exception> {
		R apply(T t) throws E;
	}

	static <T, R, E extends Exception> Function<T, R> fWrapper(FunctionWithEx<T, R, E> fe) {
		return arg -> {
			try {
				return fe.apply(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@FunctionalInterface
	static interface BiFunctionWithEx<T, U, R, E extends Exception> {
		R apply(T t, U u) throws E;
	}

	static <T, U, R, E extends Exception> BiFunction<T, U, R> bfWrapper(BiFunctionWithEx<T, U, R, E> fe) {
		return (arg1, arg2) -> {
			try {
				return fe.apply(arg1, arg2);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@FunctionalInterface
	static interface IntConsumerWithEx<E extends Exception> {
		void accept(int i) throws E;
	}

	static <E extends Exception> IntConsumer icWrapper(IntConsumerWithEx<E> ice) {
		return i -> {
			try {
				ice.accept(i);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@FunctionalInterface
	static interface ConsumerWithEx<T, E extends Exception> {
		void accept(T t) throws E;
	}

	static <T, E extends Exception> Consumer<T> cWrapper(ConsumerWithEx<T, E> ce) {
		return arg -> {
			try {
				ce.accept(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@FunctionalInterface
	static interface BiConsumerWithEx<T, U, E extends Exception> {
		void accept(T t, U u) throws E;
	}

	static <T, U, E extends Exception> BiConsumer<T, U> bcWrapper(BiConsumerWithEx<T, U, E> ce) {
		return (arg1, arg2) -> {
			try {
				ce.accept(arg1, arg2);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@FunctionalInterface
	static interface PredicateWithEx<T, E extends Exception> {
		boolean test(T t) throws E;
	}

	static <T, E extends Exception> Predicate<T> pWrapper(PredicateWithEx<T, E> pe) {
		return arg -> {
			try {
				return pe.test(arg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@FunctionalInterface
	static interface RunnableWithEx {
		void run() throws Exception;
	}

	static Runnable rWrapper(RunnableWithEx re) {
		return () -> {
			try {
				re.run();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}

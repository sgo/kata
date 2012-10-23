package kata;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

/**
 * for entity inherits data and ops then:
 * <p/>
 * data.setField("value") => entity.getField() == "value"
 * entity.setField("value") => data.getField() == "value"
 * entity.operationWithoutReturnValue() == ops.operationWithoutReturnValue()
 * entity.operationWithReturnValue() == ops.operationWithReturnValue()
 * entity.setlookslikesetter("value") == ops.setlookslikesetter("value")
 * entity.getlookslikegetter() == ops.getlookslikegetter()
 * entity.setNoArgs() == ops.setNoArgs()
 * entity.setTwoArg("arg1", "arg2") == ops.setTwoArgs("arg1", "arg2")
 * entity.getWithArgs("arg") == ops.getWithArgs("arg")
 * entity.getWithoutReturnValue() == ops.getWithoutReturnValue()
 */
public class DataOpsDispatcherBuilderTest {

	private DataOpsDispatcherBuilder<Entity> builder = new DataOpsDispatcherBuilder<Entity>();
	private Data data = new Data();
	private Handler handler = new Handler(data);
	private Entity entity;
	
	@Before public void createEntity() {
		entity = builder.type(Entity.class).data(data).operations(handler).toEntity();
	}

	@Test public void delegateAccessorsToData() {
		assertNull(entity.getField());
		
		data.setField("field");
		assertEquals("field", entity.getField());
		
		entity.setField("changed");
		assertEquals("changed", data.getField());
	}
	
	@Test public void delegateOpsToHandler() {
		entity.voidOp();
		assertTrue(handler.invokedVoidOp);
		
		data.setField("field");
		assertEquals("operated on field", entity.returnOp());
		
		entity.setlookslikesetter("arg");
		assertEquals("arg", handler.receivedArg);
		
		entity.setAlsoLooksLikeSetter();
		assertTrue(handler.receivedNoArgSetter);

		entity.setTwoArgs("arg1", "arg2");
		assertEquals("arg1", handler.receivedArg1);
		assertEquals("arg2", handler.receivedArg2);
		
		entity.getlookslikegetter();
		assertTrue(handler.invokedLooksLikeGetter);
		
		entity.getWithArgs("arg");
		assertEquals("arg", handler.receivedGetterArgs);
		
		entity.getVoid();
		assertTrue(handler.invokedVoidGetter);
	}
	
	public static interface Entity extends Accessors, Operations {
	}
	
	public static interface Accessors {
		void setField(String field);
		String getField();
	}
	
	public static interface Operations {
		void voidOp();
		String returnOp();
		void setlookslikesetter(String arg);
		void setAlsoLooksLikeSetter();
		void setTwoArgs(String arg1, String arg2);
		void getlookslikegetter();
		void getWithArgs(String arg);
		void getVoid();
	}

	private class Data implements Accessors {
		private String field;
		
		@Override public void setField(String field) {
			this.field = field;
		}

		@Override public String getField() {
			return field;
		}
	}
	
	private class Handler implements Operations {
		private Data data;
		public boolean invokedVoidOp;
		public String receivedArg;
		public boolean receivedNoArgSetter;
		public String receivedArg1;
		public String receivedArg2;
		public boolean invokedLooksLikeGetter;
		public String receivedGetterArgs;
		public boolean invokedVoidGetter;

		public Handler(Data data) {
			this.data = data;
		}

		@Override public void voidOp() {
			invokedVoidOp = true;
		}

		@Override public String returnOp() {
			return "operated on " + data.getField();
		}

		@Override public void setlookslikesetter(String arg) {
			receivedArg = arg;
		}

		@Override public void setAlsoLooksLikeSetter() {
			receivedNoArgSetter = true;
		}

		@Override public void setTwoArgs(String arg1, String arg2) {
			receivedArg1 = arg1;
			receivedArg2 = arg2;
		}

		@Override public void getlookslikegetter() {
			invokedLooksLikeGetter = true;
		}

		@Override public void getWithArgs(String arg) {
			receivedGetterArgs = arg;
		}

		@Override public void getVoid() {
			invokedVoidGetter = true;
		}
	}
}

package kata;

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

}

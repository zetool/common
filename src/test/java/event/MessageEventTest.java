package event;

import static org.zetool.test.StandardAssert.assertDataAccess;

import org.junit.Test;

public class MessageEventTest {
    private static enum TestEnum {
        A, B;
    }

    @Test
    public void testData() {
        Object source = new Object();
        MessageEvent<Object, TestEnum> event = new MessageEvent<>(source, TestEnum.B, "message text");

        assertDataAccess(event, MessageEvent::getSource, source);
        assertDataAccess(event, MessageEvent::getMessage, "message text");
        assertDataAccess(event, MessageEvent::getType, TestEnum.B);
    }

}

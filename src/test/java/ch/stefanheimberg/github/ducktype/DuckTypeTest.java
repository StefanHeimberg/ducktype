/*
 * Copyright 2015 Stefan Heimberg <kontakt@stefanheimberg.ch>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.stefanheimberg.github.ducktype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import org.junit.Test;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public class DuckTypeTest {

    @Test
    public void testCast_DuckObject1() {
        final MyDuckObject1 duckObject = new MyDuckObject1();
        final MyDuck duck = DuckType.cast(duckObject, MyDuck.class);
        assertNotSame(duckObject, duck);
        assertEquals(duckObject.duck(), duck.duck());
    }

    @Test
    public void testCast_DuckObject2() {
        final MyDuckObject2 duckObject = new MyDuckObject2();
        final MyDuck duck = DuckType.cast(duckObject, MyDuck.class);
        assertNotSame(duckObject, duck);
        assertEquals(duckObject.duck(), duck.duck());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCast_DuckTypeNotInterface() {
        final MyDuckObject4 expected = new MyDuckObject4();
        DuckType.cast(expected, MyDuckObject3.class);
    }

    @Test
    public void testCast_ImplementsDuckInterface() {
        final MyDuckObject4 expected = new MyDuckObject4();
        final MyDuck actual = DuckType.cast(expected, MyDuck.class);
        assertSame(expected, actual);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCast_MethodNotImplemented() {
        final MyDuckObject1 duckObject = new MyDuckObject1();
        final MyDuck duck = DuckType.cast(duckObject, MyDuck.class);
        assertEquals(duck.duck2(), duck.duck());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCast_MethodSignatureNotMatch() {
        final MyDuckObject3 duckObject = new MyDuckObject3();
        final MyDuck duck = DuckType.cast(duckObject, MyDuck.class);
        assertNotSame(duckObject, duck);
        assertEquals(duckObject.duck("hallo"), duck.duck());
    }

}

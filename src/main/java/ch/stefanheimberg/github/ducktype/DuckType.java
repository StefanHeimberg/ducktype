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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author Stefan Heimberg <kontakt@stefanheimberg.ch>
 */
public class DuckType {

    public static <T_DUCK> T_DUCK cast(final Object object, final Class<T_DUCK> duckType) {
        if (null == object) {
            return null;
        }

        if (duckType.isInstance(object)) {
            return duckType.cast(object);
        }

        final T_DUCK duck = (T_DUCK) Proxy.newProxyInstance(duckType.getClassLoader(), new Class[]{duckType}, (Object proxy, Method method, Object[] args) -> {
            try {
                return object.getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(object, args);
            } catch (final NoSuchMethodException ex) {
                throw new NoSuchMethodError(ex.getMessage());
            } catch (final InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        });

        return duckType.cast(duck);
    }

}

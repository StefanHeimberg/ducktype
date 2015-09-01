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

import org.apache.commons.lang3.Validate;
import org.apache.commons.proxy.ObjectProvider;
import org.apache.commons.proxy.ProxyFactory;
import org.apache.commons.proxy.invoker.DuckTypingInvoker;
import org.apache.commons.proxy.provider.ConstantProvider;

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

        final ObjectProvider provider = new ConstantProvider(object);
        final DuckTypingInvoker invoker = new DuckTypingInvoker(provider);
        final ProxyFactory factory = new ProxyFactory();
        final Object duck = factory.createInvokerProxy(invoker, new Class[]{duckType});

        Validate.notNull(duck);
        return duckType.cast(duck);
    }

}

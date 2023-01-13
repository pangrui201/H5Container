package com.haier.uhome.h5container.interceptor;

import java.util.HashMap;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/10 16:09
 */
    public class UniqueKeyMap<K, V> extends HashMap<K, V> {
        private String tipText;

        public UniqueKeyMap(String exceptionText) {
            super();
            tipText = exceptionText;
        }

        @Override
        public V put(K key, V value) {
            if (containsKey(key)) {
                throw new RuntimeException(String.format(tipText, key));
            } else {
                return super.put(key, value);
            }
        }
}

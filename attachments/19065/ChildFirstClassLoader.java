/*
 * The MIT License
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

/**
 * {@link ClassLoader} that load classes and resources with a hierarchy.
 *
 * <p>
 * This code is used to create an isolated environment.
 *
 * @author olamy
 */
public class ChildFirstClassLoader extends ClassLoader {
    /**
     * Prefix of the packages that should be hidden.
     */
    private final String[] masks;
    
    private final URLClassLoader child;

    public ChildFirstClassLoader(ClassLoader parent, URLClassLoader child, String... masks) {
        super(parent);
        this.child = child;
        this.masks = masks;
    }

    private boolean matches(String name) {
        for (String mask : masks)
            if (name.startsWith(mask))
                return true;
        return false;
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = null;
        if (matches(name))
            clazz = child.loadClass(name);
        return clazz == null ? super.loadClass(name, resolve) : clazz;
    }

    @Override
    public URL getResource(String name) {
        URL resource = null;
        if (matches(name))
            resource = child.getResource(name);
        return resource == null ? super.getResource(name) : resource;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        Enumeration<URL> urls = null;
        if (matches(name))
            urls = child.getResources(name);
        return urls == null ? super.getResources(name) : urls;
    }

    @Override
    protected URL findResource(String name) {
        URL resource = null;
        if (matches(name))
            resource = child.findResource(name);
        return resource == null ? super.findResource(name) : resource;
    }

    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        Enumeration<URL> urls = null;
        if (matches(name))
            urls = child.findResources(name);
        return urls == null ? super.findResources(name) : urls;
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream resource = null;
        if (matches(name))
            resource = child.getResourceAsStream(name);
        return resource == null ? super.getResourceAsStream(name) : resource;
    }
}


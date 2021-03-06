/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package org.olap4j.impl;

import junit.framework.TestCase;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Test case for {@link Base64}.
 *
 * @author Brian Burton, Julian Hyde
 */
public class Base64Test extends TestCase
{
    private static final long SEED = 12345678;
    private static Random s_random = new Random(SEED);

    private byte[] createData(int length) throws Exception
    {
        byte[] bytes = new byte[length];
        s_random.nextBytes(bytes);
        return bytes;
    }

    private void runStreamTest(int length) throws Exception
    {
        byte[] data = createData(length);
        ByteArrayOutputStream out_bytes = new ByteArrayOutputStream();
        OutputStream out = new Base64.OutputStream(out_bytes);
        out.write(data);
        out.close();
        byte[] encoded = out_bytes.toByteArray();
        byte[] decoded = Base64.decode(encoded, 0, encoded.length);
        assertTrue(Arrays.equals(data, decoded));

        Base64.InputStream in =
            new Base64.InputStream(new ByteArrayInputStream(encoded));
        out_bytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[3];
        for (int n = in.read(buffer); n > 0; n = in.read(buffer)) {
            out_bytes.write(buffer, 0, n);
        }
        out_bytes.close();
        in.close();
        decoded = out_bytes.toByteArray();
        assertTrue(Arrays.equals(data, decoded));
    }

    public void testStreams() throws Exception
    {
        for (int i = 0; i < 100; ++i) {
            runStreamTest(i);
        }
        for (int i = 100; i < 2000; i += 250) {
            runStreamTest(i);
        }
        for (int i = 2000; i < 80000; i += 1000) {
            runStreamTest(i);
        }
    }

    public void testSimple()
    {
        String s =
            "Man is distinguished, not only by his reason, but by this "
            + "singular passion from other animals, which is a lust of the "
            + "mind, that by a perseverance of delight in the continued and "
            + "indefatigable generation of knowledge, exceeds the short "
            + "vehemence of any carnal pleasure.";
        String encoded = Base64.encodeBytes(s.getBytes());
        String expected =
            "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlz\n"
            + "IHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3aGljaCBpcyBhIGx1c3Qgb2Yg\n"
            + "dGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFuY2Ugb2YgZGVsaWdodCBpbiB0aGUgY29udGlu\n"
            + "dWVkIGFuZCBpbmRlZmF0aWdhYmxlIGdlbmVyYXRpb24gb2Yga25vd2xlZGdlLCBleGNlZWRzIHRo\n"
            + "ZSBzaG9ydCB2ZWhlbWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4=";
        assertEquals(expected, encoded);
        byte[] s1 = Base64.decode(encoded);
        assertEqualsByteArray(s.getBytes(), s1);
    }

    private void assertEqualsByteArray(byte[] bytes, byte[] bytes1) {
        assertEquals(bytes.length, bytes1.length);
        for (int i = 0; i < bytes.length; i++) {
            assertEquals(bytes[i], bytes1[i]);
        }
    }
}

// End Base64Test.java

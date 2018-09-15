/*
 * Copyright (c) Fabio Falcinelli 2016.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.ffalcinelli.jdivert.headers;

import java.nio.ByteBuffer;

import static com.github.ffalcinelli.jdivert.Util.printHexBinary;

/**
 * Created by fabio on 25/10/2016.
 */
public class Icmpv6 extends Icmp {

    public Icmpv6(ByteBuffer raw, int start) {
        super(raw, start);
    }

    public byte[] getMessageBody() {
        return getBytesAtOffset(start + 4, 4);
    }

    public void setMessageBody(byte[] messageBody) {
        setBytesAtOffset(start + 4, messageBody.length, messageBody);
    }

    @Override
    public String toString() {
        return String.format("ICMPv6 {type=%d, code=%d, cksum=%s, messageBody=%s}"
                , getType()
                , getCode()
                , Integer.toHexString(getChecksum())
                , printHexBinary(getMessageBody())
        );
    }
}

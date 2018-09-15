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

package com.github.ffalcinelli.jdivert.windivert;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

/**
 * Represents the "address" of a captured or injected packet. The address
 * includes the packet's headers interfaces and the packet direction. Created by
 * fabio on 20/10/2016.
 */
public class WinDivertAddress extends Structure {
	public WinDef.LONG Timestamp;
	public WinDef.UINT IfIdx;
	public WinDef.UINT SubIfIdx;
	public WinDef.USHORT Direction;
	public WinDef.USHORT Loopback;
	public WinDef.USHORT Impostor;
	public WinDef.USHORT PseudoIPChecksum;
	public WinDef.USHORT PseudoTCPChecksum;
	public WinDef.USHORT PseudoUDPChecksum;

	@Override
	protected List getFieldOrder() {
		return Arrays.asList("Timestamp", "IfIdx", "SubIfIdx", "Direction", "Loopback", "Impostor", "PseudoIPChecksum",
				"PseudoTCPChecksum", "PseudoUDPChecksum");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		WinDivertAddress that = (WinDivertAddress) o;

		return Timestamp.longValue() == that.Timestamp.longValue() && IfIdx.intValue() == that.IfIdx.intValue()
				&& SubIfIdx.intValue() == that.SubIfIdx.intValue() && Direction.intValue() == that.Direction.intValue()
				&& Loopback.intValue() == that.Loopback.intValue() && Impostor.intValue() == that.Impostor.intValue()
				&& PseudoIPChecksum.intValue() == that.PseudoIPChecksum.intValue()
				&& PseudoTCPChecksum.intValue() == that.PseudoTCPChecksum.intValue()
				&& PseudoUDPChecksum.intValue() == that.PseudoUDPChecksum.intValue();

	}

	@Override
	public int hashCode() {
		int result = 31 * Timestamp.hashCode();
		result = 31 * result + IfIdx.hashCode();
		result = 31 * result + SubIfIdx.hashCode();
		result = 31 * result + Direction.hashCode();
		result = 31 * result + Loopback.hashCode();
		result = 31 * result + Impostor.hashCode();
		result = 31 * result + PseudoIPChecksum.hashCode();
		result = 31 * result + PseudoTCPChecksum.hashCode();
		result = 31 * result + PseudoUDPChecksum.hashCode();
		return result;
	}
}
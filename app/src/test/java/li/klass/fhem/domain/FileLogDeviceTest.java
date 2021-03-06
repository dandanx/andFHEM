/*
 * AndFHEM - Open Source Android application to control a FHEM home automation
 * server.
 *
 * Copyright (c) 2011, Matthias Klass or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU GENERAL PUBLIC LICENSE, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU GENERAL PUBLIC LICENSE
 * for more details.
 *
 * You should have received a copy of the GNU GENERAL PUBLIC LICENSE
 * along with this distribution; if not, write to:
 *   Free Software Foundation, Inc.
 *   51 Franklin Street, Fifth Floor
 *   Boston, MA  02110-1301  USA
 */

package li.klass.fhem.domain;

import li.klass.fhem.domain.core.DeviceXMLParsingBase;
import org.junit.Test;

import static li.klass.fhem.domain.FileLogDevice.extractConcerningDeviceNameFromDefinition;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class FileLogDeviceTest extends DeviceXMLParsingBase {
    @Test
    public void testForCorrectlySetAttributes() {
        FileLogDevice device = getDefaultDevice();

        assertThat(device.getName(), is(DEFAULT_TEST_DEVICE_NAME));
        assertThat(device.getRoomConcatenated(), is(DEFAULT_TEST_ROOM_NAME));

        assertThat(device.getConcerningDeviceName(), is("myRegexp"));
        assertThat(device.getState(), is("active"));

        assertThat(device.getAvailableTargetStates(), is(notNullValue()));

        assertThat(device.getFileLog(), is(nullValue()));
        assertThat(device.getDeviceCharts().size(), is(0));

        assertThat(device.getCustomGraphs().size(), is(2));
        assertThat(device.getCustomGraphs(), hasItem(new FileLogDevice.CustomGraph("4:", "someValue", "axis1")));
        assertThat(device.getCustomGraphs(), hasItem(new FileLogDevice.CustomGraph("46:", "someValue with space", "axis")));
    }

    @Test
    public void testExtractConcerningDeviceNameFromDefinition() {
        assertThat(extractConcerningDeviceNameFromDefinition("(Thermostat_Bad:.*(temperature|battery|desiredTemperature|valveposition)):|(Temp_Sensor_Schlafzimmer:.*temperature).*"), is("Thermostat_Bad"));
        assertThat(extractConcerningDeviceNameFromDefinition("CUL_TX_116:T:.*"), is("CUL_TX_116"));
    }

    @Override
    protected String getFileName() {
        return "filelog.xml";
    }
}

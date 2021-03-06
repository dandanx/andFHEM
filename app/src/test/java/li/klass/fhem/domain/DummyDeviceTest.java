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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class DummyDeviceTest extends DeviceXMLParsingBase {
    @Test
    public void testForCorrectlySetAttributesInOnOffDummy() {
        DummyDevice device = getDefaultDevice();

        assertThat(device.getName(), is(DEFAULT_TEST_DEVICE_NAME));
        assertThat(device.getRoomConcatenated(), is(DEFAULT_TEST_ROOM_NAME));

        assertThat(device.getState(), is("on"));
        assertThat(device.isSpecialButtonDevice(), is(false));
        assertThat(device.supportsToggle(), is(true));
        assertThat(device.isOnByState(), is(true));

        assertThat(device.getAvailableTargetStates(), hasItemInArray("on"));
        assertThat(device.getAvailableTargetStates(), hasItemInArray("off"));

        assertThat(device.getFileLog(), is(nullValue()));
        assertThat(device.getDeviceCharts().size(), is(0));
        assertThat(device.supportsDim(), is(false));
    }

    @Test
    public void testForCorrectlySetAttributesInCommonDummy() {
        DummyDevice device = getDeviceFor("device1");

        assertThat(device.getName(), is("device1"));
        assertThat(device.getRoomConcatenated(), is(DEFAULT_TEST_ROOM_NAME));

        assertThat(device.getState(), is("??"));
        assertThat(device.isSpecialButtonDevice(), is(false));
        assertThat(device.supportsToggle(), is(false));

        assertThat(device.getFileLog(), is(nullValue()));
        assertThat(device.getDeviceCharts().size(), is(0));
        assertThat(device.supportsDim(), is(false));
    }

    @Test
    public void testDeviceWithSetList() {
        DummyDevice device = getDeviceFor("deviceWithSetlist");

        assertThat(device.getAvailableTargetStates(), is(arrayContaining("17", "18", "19", "20", "21", "21.5", "22")));
        assertThat(device.getAvailableTargetStates().length, is(7));
        assertThat(device.supportsDim(), is(false));
    }

    @Test
    public void testDeviceWithTimer() {
        DummyDevice device = getDeviceFor("timerDevice");

        assertThat(device.isTimerDevice(), is(true));
        assertThat(device.supportsDim(), is(false));
    }

    @Test
    public void testSliderDevice() {
        DummyDevice device = getDeviceFor("sliderDevice");
        assertThat(device.supportsDim(), is(true));

        assertThat(device.getDimUpperBound(), is(50));
        assertThat(device.getDimLowerBound(), is(10));
        assertThat(device.getDimStep(), is(2));
    }

    @Test
    public void testEventMapDevice() {
        DummyDevice device = getDeviceFor("eventMapDevice");

        String[] eventMapStates = device.getAvailableTargetStatesEventMapTexts();
        assertThat(eventMapStates, is(notNullValue()));

        String[] targetStates = device.getAvailableTargetStates();
        assertThat(targetStates, is(notNullValue()));

        assertThat(targetStates.length, is(eventMapStates.length));

        assertThat(targetStates, hasItemInArray("oben"));
        assertThat(targetStates, hasItemInArray("unten"));
        assertThat(targetStates, hasItemInArray("65"));
        assertThat(targetStates, hasItemInArray("40"));

        assertThat(eventMapStates, hasItemInArray("Oben"));
        assertThat(eventMapStates, hasItemInArray("Unten"));
        assertThat(eventMapStates, hasItemInArray("Halbschatten"));
        assertThat(eventMapStates, hasItemInArray("Vollschatten"));
    }

    @Override
    protected String getFileName() {
        return "dummy.xml";
    }
}

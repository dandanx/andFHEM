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

package li.klass.fhem.domain.core;

import li.klass.fhem.adapter.devices.CULHMAdapter;
import li.klass.fhem.adapter.devices.DummyAdapter;
import li.klass.fhem.adapter.devices.FHTAdapter;
import li.klass.fhem.adapter.devices.FS20ZDRDeviceAdapter;
import li.klass.fhem.adapter.devices.FloorplanAdapter;
import li.klass.fhem.adapter.devices.GCMSendDeviceAdapter;
import li.klass.fhem.adapter.devices.HueDeviceAdapter;
import li.klass.fhem.adapter.devices.MaxAdapter;
import li.klass.fhem.adapter.devices.PidAdapter;
import li.klass.fhem.adapter.devices.RemoteControlAdapter;
import li.klass.fhem.adapter.devices.SonosPlayerAdapter;
import li.klass.fhem.adapter.devices.SwitchActionRowAdapter;
import li.klass.fhem.adapter.devices.ToggleableAdapterWithSwitchActionRow;
import li.klass.fhem.adapter.devices.UniRollAdapter;
import li.klass.fhem.adapter.devices.WOLAdapter;
import li.klass.fhem.adapter.devices.WeatherAdapter;
import li.klass.fhem.adapter.devices.YamahaAVRAdapter;
import li.klass.fhem.adapter.devices.core.DeviceAdapter;
import li.klass.fhem.adapter.devices.core.DimmableAdapter;
import li.klass.fhem.adapter.devices.core.GenericDeviceAdapter;
import li.klass.fhem.adapter.devices.core.ToggleableAdapter;
import li.klass.fhem.domain.AtDevice;
import li.klass.fhem.domain.CULEMDevice;
import li.klass.fhem.domain.CULFHTTKDevice;
import li.klass.fhem.domain.CULHMDevice;
import li.klass.fhem.domain.CULTXDevice;
import li.klass.fhem.domain.CULWSDevice;
import li.klass.fhem.domain.DummyDevice;
import li.klass.fhem.domain.EIBDevice;
import li.klass.fhem.domain.EMWZDevice;
import li.klass.fhem.domain.ESA2000Device;
import li.klass.fhem.domain.EnOceanDevice;
import li.klass.fhem.domain.FBCallmonitorDevice;
import li.klass.fhem.domain.FBDectDevice;
import li.klass.fhem.domain.FHT8VDevice;
import li.klass.fhem.domain.FHTDevice;
import li.klass.fhem.domain.FRMInDevice;
import li.klass.fhem.domain.FRMOutDevice;
import li.klass.fhem.domain.FS20Device;
import li.klass.fhem.domain.FS20ZDRDevice;
import li.klass.fhem.domain.FileLogDevice;
import li.klass.fhem.domain.FloorplanDevice;
import li.klass.fhem.domain.GCMSendDevice;
import li.klass.fhem.domain.GPIO4Device;
import li.klass.fhem.domain.GenShellSwitchDevice;
import li.klass.fhem.domain.HCSDevice;
import li.klass.fhem.domain.HMSDevice;
import li.klass.fhem.domain.HOLDevice;
import li.klass.fhem.domain.HUEDevice;
import li.klass.fhem.domain.IntertechnoDevice;
import li.klass.fhem.domain.KS300Device;
import li.klass.fhem.domain.LGTVDevice;
import li.klass.fhem.domain.MaxDevice;
import li.klass.fhem.domain.OWFSDevice;
import li.klass.fhem.domain.OpenWeatherMapDevice;
import li.klass.fhem.domain.OregonDevice;
import li.klass.fhem.domain.OwDevice;
import li.klass.fhem.domain.OwcountDevice;
import li.klass.fhem.domain.OwtempDevice;
import li.klass.fhem.domain.OwthermDevice;
import li.klass.fhem.domain.PCA301Device;
import li.klass.fhem.domain.PIDDevice;
import li.klass.fhem.domain.PresenceDevice;
import li.klass.fhem.domain.RFXCOMDevice;
import li.klass.fhem.domain.RFXX10RECDevice;
import li.klass.fhem.domain.RemoteControlDevice;
import li.klass.fhem.domain.SISPMSDevice;
import li.klass.fhem.domain.SWAPDevice;
import li.klass.fhem.domain.SonosDevice;
import li.klass.fhem.domain.SonosPlayerDevice;
import li.klass.fhem.domain.StructureDevice;
import li.klass.fhem.domain.TRXDevice;
import li.klass.fhem.domain.TRXLightDevice;
import li.klass.fhem.domain.TRXSecurityDevice;
import li.klass.fhem.domain.TRXWeatherDevice;
import li.klass.fhem.domain.TwilightDevice;
import li.klass.fhem.domain.USBWXDevice;
import li.klass.fhem.domain.UniRollDevice;
import li.klass.fhem.domain.WOLDevice;
import li.klass.fhem.domain.WatchdogDevice;
import li.klass.fhem.domain.WeatherDevice;
import li.klass.fhem.domain.YamahaAVRDevice;
import li.klass.fhem.domain.ZWaveDevice;
import li.klass.fhem.fhem.ConnectionType;
import li.klass.fhem.util.ApplicationProperties;

public enum DeviceType {
    KS300("KS300", KS300Device.class),
    WEATHER("Weather", WeatherDevice.class, new WeatherAdapter()),
    FLOORPLAN("FLOORPLAN", FloorplanDevice.class, new FloorplanAdapter(), ConnectionType.FHEMWEB),
    FHT("FHT", FHTDevice.class, new FHTAdapter()),
    CUL_TX("CUL_TX", CULTXDevice.class),
    HMS("HMS", HMSDevice.class),
    MAX("MAX", MaxDevice.class, new MaxAdapter()),
    WOL("WOL", WOLDevice.class, new WOLAdapter()),
    IT("IT", IntertechnoDevice.class, new ToggleableAdapter<IntertechnoDevice>(IntertechnoDevice.class)),
    OWTEMP("OWTEMP", OwtempDevice.class),
    CUL_FHTTK("CUL_FHTTK", CULFHTTKDevice.class),
    RFXX10REC("RFXX10REC", RFXX10RECDevice.class),
    OREGON("OREGON", OregonDevice.class),
    CUL_EM("CUL_EM", CULEMDevice.class),
    OWCOUNT("OWCOUNT", OwcountDevice.class),
    SIS_PMS("SIS_PMS", SISPMSDevice.class, new ToggleableAdapter<SISPMSDevice>(SISPMSDevice.class)),
    USBWX("USBWX", USBWXDevice.class),
    CUL_WS("CUL_WS", CULWSDevice.class),
    FS20("FS20", FS20Device.class, new DimmableAdapter<FS20Device>(FS20Device.class)),
    FILE_LOG("FileLog", FileLogDevice.class, null, ConnectionType.NEVER),
    OWFS("OWFS", OWFSDevice.class),
    LGTV("LGTV", LGTVDevice.class),
    RFXCOM("RFXCOM", RFXCOMDevice.class),
    CUL_HM("CUL_HM", CULHMDevice.class, new CULHMAdapter()),
    WATCHDOG("watchdog", WatchdogDevice.class),
    HOLIDAY("HOL", HOLDevice.class, new ToggleableAdapter<HOLDevice>(HOLDevice.class)),
    PID("PID", PIDDevice.class, new PidAdapter(PIDDevice.class)),
    FHT8V("FHT8V", FHT8VDevice.class),
    TRX_WEATHER("TRX_WEATHER", TRXWeatherDevice.class),
    TRX_LIGHT("TRX_LIGHT", TRXLightDevice.class, new DimmableAdapter<TRXLightDevice>(TRXLightDevice.class)),
    TRX("TRX", TRXDevice.class),
    DUMMY("dummy", DummyDevice.class, new DummyAdapter()),
    STRUCTURE("structure", StructureDevice.class, new ToggleableAdapterWithSwitchActionRow<StructureDevice>(StructureDevice.class)),
    TWILIGHT("Twilight", TwilightDevice.class),
    AT("at", AtDevice.class, null, ConnectionType.NEVER),
    EN_OCEAN("EnOcean", EnOceanDevice.class, new DimmableAdapter<EnOceanDevice>(EnOceanDevice.class)),
    EIB("EIB", EIBDevice.class, new DimmableAdapter<EIBDevice>(EIBDevice.class)),
    HCS("HCS", HCSDevice.class, new SwitchActionRowAdapter<HCSDevice>(HCSDevice.class)),
    OWTHERM("OWTHERM", OwthermDevice.class),
    OWDEVICE("OWDevice", OwDevice.class, new ToggleableAdapterWithSwitchActionRow<OwDevice>(OwDevice.class)),
    UNIROLL("UNIRoll", UniRollDevice.class, new UniRollAdapter()),
    TRXSecurity("TRX_SECURITY", TRXSecurityDevice.class, new SwitchActionRowAdapter<TRXSecurityDevice>(TRXSecurityDevice.class)),
    PRESENCE("PRESENCE", PresenceDevice.class),
    EMWZ("EMWZ", EMWZDevice.class),
    FBDect("FBDECT", FBDectDevice.class, new ToggleableAdapterWithSwitchActionRow<FBDectDevice>(FBDectDevice.class)),
    SONOS_PLAYER("SONOSPLAYER", SonosPlayerDevice.class, new SonosPlayerAdapter()),
    SONOS("SONOS", SonosDevice.class),
    GPIO4("GPIO4", GPIO4Device.class),
    FRMOUT("FRM_OUT", FRMOutDevice.class, new ToggleableAdapterWithSwitchActionRow<FRMOutDevice>(FRMOutDevice.class)),
    ESA2000("ESA2000", ESA2000Device.class),
    HUE("HUEDevice", HUEDevice.class, new HueDeviceAdapter()),
    YAMAHA_AVR("YAMAHA_AVR", YamahaAVRDevice.class, new YamahaAVRAdapter()),
    FRMIN("FRM_IN", FRMInDevice.class),
    GENSHELLSWITCH("GenShellSwitch", GenShellSwitchDevice.class, new ToggleableAdapterWithSwitchActionRow<GenShellSwitchDevice>(GenShellSwitchDevice.class)),
    GCM_SEND("gcmsend", GCMSendDevice.class, new GCMSendDeviceAdapter()),
    ZWAVE("ZWave", ZWaveDevice.class, new DimmableAdapter<ZWaveDevice>(ZWaveDevice.class)),
    SWAP("SWAP", SWAPDevice.class),
    FB_CALLMONITOR("FB_CALLMONITOR", FBCallmonitorDevice.class),
    FS20_ZDR("fs20_zdr", FS20ZDRDevice.class, new FS20ZDRDeviceAdapter()),
    OPENWEATHERMAP("openweathermap", OpenWeatherMapDevice.class),
    PCA301("PCA301", PCA301Device.class),
    REMOTECONTROL("remotecontrol", RemoteControlDevice.class, new RemoteControlAdapter(), ConnectionType.FHEMWEB)
    ;

    private String xmllistTag;
    private Class<? extends Device> deviceClass;
    private DeviceAdapter<? extends Device<?>> adapter;
    private ConnectionType showDeviceTypeOnlyInConnection = null;

    <T extends Device<T>> DeviceType(String xmllistTag, Class<T> deviceClass) {
        this(xmllistTag, deviceClass, new GenericDeviceAdapter<T>(deviceClass));
    }

    DeviceType(String xmllistTag, Class<? extends Device> deviceClass, DeviceAdapter<? extends Device<?>> adapter) {
        this.xmllistTag = xmllistTag;
        this.deviceClass = deviceClass;
        this.adapter = adapter;
    }

    DeviceType(String xmllistTag, Class<? extends Device> deviceClass, DeviceAdapter<? extends Device<?>> adapter, ConnectionType showDeviceTypeOnlyIn) {
        this(xmllistTag, deviceClass, adapter);
        showDeviceTypeOnlyInConnection = showDeviceTypeOnlyIn;
    }

    public String getXmllistTag() {
        return xmllistTag;
    }

    public Class<? extends Device> getDeviceClass() {
        return deviceClass;
    }

    @SuppressWarnings("unchecked")
    public <T extends Device> DeviceAdapter<T> getAdapter() {
        return (DeviceAdapter<T>) adapter;
    }

    public boolean mayEverShow() {
        return showDeviceTypeOnlyInConnection != ConnectionType.NEVER;
    }

    public boolean mayShowInCurrentConnectionType() {
        if (showDeviceTypeOnlyInConnection == null) return true;

        ConnectionType connectionType = ApplicationProperties.INSTANCE.getConnectionType();
        return connectionType == showDeviceTypeOnlyInConnection;
    }

    public static <T extends Device> DeviceAdapter<T> getAdapterFor(T device) {
        DeviceType deviceType = getDeviceTypeFor(device);
        if (deviceType == null) {
            return null;
        } else {
            return deviceType.getAdapter();
        }
    }

    public static <T extends Device> DeviceType getDeviceTypeFor(T device) {
        if (device == null) return null;

        DeviceType[] deviceTypes = DeviceType.values();
        for (DeviceType deviceType : deviceTypes) {
            if (deviceType.getDeviceClass().isAssignableFrom(device.getClass())) {
                return deviceType;
            }
        }
        return null;
    }
}
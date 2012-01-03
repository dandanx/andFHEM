package li.klass.fhem.service.favorites;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import li.klass.fhem.AndFHEMApplication;
import li.klass.fhem.domain.Device;
import li.klass.fhem.domain.RoomDeviceList;
import li.klass.fhem.service.room.RoomDeviceListListener;
import li.klass.fhem.service.room.RoomListService;

import java.util.Set;

public class FavoritesService {

    public static final FavoritesService INSTANCE = new FavoritesService();
    private static final String PREFERENCES_NAME = "favorites";

    private FavoritesService() {
    }

    public void addFavorite(Device device) {
        getPreferences()
                .edit().
                putString(device.getName(), device.getName())
                .commit();
    }

    public void removeFavorite(Device device) {
        getPreferences().edit().remove(device.getName()).commit();
    }

    public void getFavorites(Context context, boolean refresh, final RoomDeviceListListener listener) {

        RoomListService.INSTANCE.getAllRoomsDeviceList(context, refresh, new RoomDeviceListListener() {

            @Override
            public void onRoomListRefresh(RoomDeviceList roomDeviceList) {
                RoomDeviceList deviceList = new RoomDeviceList("favorites");
                Set<String> favoriteDeviceNames = getPreferences().getAll().keySet();
                for (String favoriteDeviceName : favoriteDeviceNames) {
                    Device device = roomDeviceList.getDeviceFor(favoriteDeviceName);
                    if (device != null) {
                        deviceList.addDevice(device);
                    }
                }

                listener.onRoomListRefresh(deviceList);
            }
        });
    }

    private SharedPreferences getPreferences() {
        return AndFHEMApplication.getContext().getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }
}
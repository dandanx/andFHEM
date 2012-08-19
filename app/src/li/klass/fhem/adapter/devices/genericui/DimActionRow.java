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

package li.klass.fhem.adapter.devices.genericui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import li.klass.fhem.AndFHEMApplication;
import li.klass.fhem.R;
import li.klass.fhem.constants.Actions;
import li.klass.fhem.constants.BundleExtraKeys;
import li.klass.fhem.domain.core.DimmableDevice;

public class DimActionRow<D extends DimmableDevice<D>> {
    private String description;
    private int layout;

    public static final int LAYOUT_DETAIL = R.layout.device_detail_seekbarrow;
    public static final int LAYOUT_OVERVIEW = R.layout.device_overview_seekbarrow;
    private D device;

    public DimActionRow(D device, int description, int layout) {
        this(device, AndFHEMApplication.getContext().getString(description), layout);
    }

    public DimActionRow(D device, String description, int layout) {
        this.description = description;
        this.layout = layout;
        this.device = device;
    }

    public TableRow createRow(LayoutInflater inflater, D device) {
        TableRow row = (TableRow) inflater.inflate(layout, null);
        ((TextView) row.findViewById(R.id.description)).setText(description);

        SeekBar seekBar = (SeekBar) row.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(createListener(device));
        seekBar.setProgress(device.getDimPosition());
        seekBar.setMax(device.getDimUpperBound());

        return row;
    }

    private SeekBar.OnSeekBarChangeListener createListener(final D device) {
        return new SeekBar.OnSeekBarChangeListener() {

            public int progress = device.getDimPosition();

            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                this.progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                DimActionRow.this.onStopTrackingTouch(seekBar.getContext(), device, progress);
            }
        };
    }

    public void onStopTrackingTouch(final Context context, D device, int progress) {
        Intent intent = new Intent(Actions.DEVICE_DIM);
        intent.putExtra(BundleExtraKeys.DEVICE_DIM_PROGRESS, progress);
        intent.putExtra(BundleExtraKeys.DEVICE_NAME, device.getName());
        intent.putExtra(BundleExtraKeys.RESULT_RECEIVER, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                context.sendBroadcast(new Intent(Actions.DO_UPDATE));
            }
        });

        context.startService(intent);
    }
}
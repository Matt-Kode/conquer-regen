package me.matt.conquerregen.towny;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.metadata.BooleanDataField;
import com.palmergames.bukkit.towny.object.metadata.CustomDataField;

public class MetaData {

    public static BooleanDataField defaultbdf = new BooleanDataField("regen", true, "Regen");

    public static Boolean getRegenBoolean(Town t) {

        if (t.hasMeta(defaultbdf.getKey())) {

            CustomDataField cdf = t.getMetadata(defaultbdf.getKey());

            if (cdf instanceof BooleanDataField) {

                BooleanDataField bdf = (BooleanDataField) cdf;

                return bdf.getValue();
            }

        }
        t.addMetaData(defaultbdf);
        return true;
    }

    public static void setRegenBoolean(Town t, Boolean value) {

        if (t.hasMeta(defaultbdf.getKey())) {
            CustomDataField cdf = t.getMetadata(defaultbdf.getKey());
            if (cdf instanceof BooleanDataField) {
                BooleanDataField idf = (BooleanDataField) cdf;

                idf.setValue(value);
                return;
            }
        }
        t.addMetaData(defaultbdf);
    }
    }



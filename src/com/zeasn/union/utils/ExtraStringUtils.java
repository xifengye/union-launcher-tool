package com.zeasn.union.utils;

import com.zeasn.union.translater.model.StringKeyMap;
import com.zeasn.union.translater.model.StringValue;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExtraStringUtils {
    private static String sourceDir = "/Users/xifengye/work/moregood/Frame/app/src/main/res/";

    private static List<StringValue> parse(String parent,String dir) throws IOException {
        String defaultString = getStringByDir(parent, dir);
        List<StringValue> values = new ArrayList<>();
        String[] lines = defaultString.split("\n");
        for(String line:lines){
            String nameLabel = "name=\"";
            String endLabel = "\">";
            int nameIndex = line.indexOf(nameLabel);
            if(nameIndex> 0){
                int endIndex = line.indexOf(endLabel);
                String key = line.substring(nameIndex+nameLabel.length(),endIndex);
                String value=line.substring(endIndex+endLabel.length(),line.length()-9);
                values.add(new StringValue(key,value));
            }
        }
        return values;
    }

    private static String getStringByDir(String parent, String dir) throws IOException {
        File file = new File(parent + dir +"/strings.xml");
        String defaultString = FileUtils.readFileToString(file,"utf-8");
        return defaultString;
    }

    public static void doSomething() throws IOException {
        List<StringValue> defaultStringValues = parse(sourceDir,"values");
        Map<String,String> stringKeyMaps = replaceKey(defaultStringValues, "values-en");
        if(defaultStringValues.size()!=stringKeyMaps.size()){
            System.out.println("数量对不上");
            return;
        }
        File sourDirFile = new File(sourceDir);
        File[] valuesFile = sourDirFile.listFiles();
        for(File file:valuesFile){
            try {
                if (file.getName().equals("values") || !file.getName().startsWith("values")) {
                    continue;
                }
                List<StringValue> stringValues = parse(sourceDir, file.getName());


                String stringFileName = file.getAbsolutePath() + "/strings.xml";
                FileUtils.deleteQuietly(new File(stringFileName));

                StringBuilder willSave = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                willSave.append("\n").append("<resources>").append("\n");
                List<StringValue> willSaveList = new ArrayList<>();
                for (StringValue sv : stringValues) {
                    if (stringKeyMaps.containsKey(sv.key)) {
                        willSaveList.add(new StringValue(stringKeyMaps.get(sv.key), sv.value));
                    }else{
//                        Log.d("%s 语种 %s 未找到",file.getName(),sv.key);
                    }
                }
                if(willSaveList.size()!= stringKeyMaps.size()){
                    Map<String,String> skm = new HashMap<>();
                    for(String key:stringKeyMaps.keySet()){
                        skm.put(stringKeyMaps.get(key),key);
                    }
                    for(StringValue isv:willSaveList){
                        skm.remove(isv.key);
                    }
                     Log.d("%s 语种：%d 应该需要：%d",file.getName(),willSaveList.size(),stringKeyMaps.size());
                    for(String notFound:skm.keySet()){
                        Log.d("%s 未找到",skm.get(notFound));
                    }
//                     continue;
                }
                Collections.sort(willSaveList, new Comparator<StringValue>() {
                    @Override
                    public int compare(StringValue o1, StringValue o2) {
                        return o1.key.compareTo(o2.key);
                    }
                });
                for (StringValue sv : willSaveList) {
                    willSave.append(String.format("\t<string name=\"%s\">%s</string>", sv.key, sv.value)).append("\n");
                }

                willSave.append("</resources>");
                FileUtils.write(new File(stringFileName), willSave.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    private static Map<String,String> replaceKey(List<StringValue> defaultStringValues, String dir) throws IOException {
        String dirValues = getStringByDir(sourceDir,dir);
        String[] lines = dirValues.split("\n");
        Map<String,String> map =new HashMap<>();
        for(String line:lines){
            String nameLabel = "name=\"";
            String endLabel = "\">";
            int nameIndex = line.indexOf(nameLabel);
            if(nameIndex> 0){
                int endIndex = line.indexOf(endLabel);
                String key = line.substring(nameIndex+nameLabel.length(),endIndex);
                String value=line.substring(endIndex+endLabel.length(),line.length()-9);
                map.put(value,key);
            }
        }
        int index = 0;
        Map<String,String> result = new HashMap<>();
        for(StringValue sv:defaultStringValues){
            String k = map.get(sv.value);
            if(k==null){
                Log.d("------------------------------%s 没有找到",sv.value);
            }
            String v = sv.key;
            if(result.containsKey(k)){
                Log.d("------------------------------%s 重复",sv.value);
            }
            result.put(k,v);
            Log.d("%d={%s-%s}",index++,k,v);
        }
        return result;
    }


}

package com.project;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DaoLlenguatge implements Dao<ObjLlenguatge> {

    private void writeList(ArrayList<ObjLlenguatge> llista) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (ObjLlenguatge llenguatge : llista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", llenguatge.getId());
                jsonObject.put("nom", llenguatge.getNom());
                jsonObject.put("any", llenguatge.getAny());
                jsonObject.put("dificultat", llenguatge.getDificultat());
                jsonObject.put("popularitat", llenguatge.getPopularitat());
                jsonArray.put(jsonObject);
            }

            PrintWriter out = new PrintWriter(Main.llenguatgesPath);
            out.write(jsonArray.toString(4));  // 4 es l'espaiat
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int getPosition(int id) {
        int result = -1;
        ArrayList<ObjLlenguatge> llista = getAll();
        for (int i = 0; i < llista.size(); i++) {
            ObjLlenguatge llenguatge = llista.get(i);
            if (llenguatge.getId() == id) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public void add(ObjLlenguatge llenguatge) {
        ArrayList<ObjLlenguatge> llista = getAll();
        ObjLlenguatge item = get(llenguatge.getId());
        if (item == null) {
            llista.add(llenguatge);
            writeList(llista);
        }
    }

    @Override
    public ObjLlenguatge get(int id) {
        ObjLlenguatge result = null;
        ArrayList<ObjLlenguatge> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            result = llista.get(posicio);
        }
        return result;
    }

    @Override
    public ArrayList<ObjLlenguatge> getAll() {
        ArrayList<ObjLlenguatge> result = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(Main.llenguatgesPath)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String nom = jsonObject.getString("nom");
                int any = jsonObject.getInt("any");
                String dificultat = jsonObject.getString("dificultat");
                int popularitat = jsonObject.getInt("popularitat");
                result.add(new ObjLlenguatge(id, nom, any, dificultat, popularitat));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(int id, ObjLlenguatge llenguatge) {
        ArrayList<ObjLlenguatge> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            llista.set(posicio, llenguatge);
            writeList(llista);
        }
    }

    @Override
    public void delete(int id) {
        ArrayList<ObjLlenguatge> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            llista.remove(posicio);
            writeList(llista);
        }
    }

    @Override
    public void print() {
        ArrayList<ObjLlenguatge> llista = getAll();
        for (int i = 0; i < llista.size(); i++) {
            System.out.println("    " + llista.get(i));
        }
    }

    @Override
    public void setNom(int id, String nom) {
        ArrayList<ObjLlenguatge> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjLlenguatge llenguatge = llista.get(posicio);
            llenguatge.setNom(nom);
            update(id, llenguatge);
        }
    }

    @Override
    public void setAny(int id, int any) {
        ArrayList<ObjLlenguatge> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjLlenguatge llenguatge = llista.get(posicio);
            llenguatge.setAny(any);
            update(id, llenguatge);
        }
    }

    public void setDificultat(int id, String dificultat) {
        ArrayList<ObjLlenguatge> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjLlenguatge llenguatge = llista.get(posicio);
            llenguatge.setDificultat(dificultat);
            update(id, llenguatge);
        }
    }

    public void setPopularitat(int id, int popularitat) {
        ArrayList<ObjLlenguatge> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjLlenguatge llenguatge = llista.get(posicio);
            llenguatge.setPopularitat(popularitat);
            update(id, llenguatge);
        }
    }
}
package com.project;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DaoSoftware implements Dao<ObjSoftware> {

    private void writeList(ArrayList<ObjSoftware> llista) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (ObjSoftware software : llista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", software.getId());
                jsonObject.put("nom", software.getNom());
                jsonObject.put("any", software.getAny());
                jsonObject.put("llenguatges", software.getLlenguatges());
                jsonArray.put(jsonObject);
            }

            PrintWriter out = new PrintWriter(Main.softwarePath);
            out.write(jsonArray.toString(4));  // 4 es l'espaiat
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int getPosition(int id) {
        int result = -1;
        ArrayList<ObjSoftware> llista = getAll();
        for (int i = 0; i < llista.size(); i++) {
            ObjSoftware software = llista.get(i);
            if (software.getId() == id) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public void add(ObjSoftware software) {
        ArrayList<ObjSoftware> llista = getAll();
        ObjSoftware item = get(software.getId());
        if (item == null) {
            llista.add(software);
            writeList(llista);
        }
    }

    @Override
    public ObjSoftware get(int id) {
        ObjSoftware result = null;
        ArrayList<ObjSoftware> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            result = llista.get(posicio);
        }
        return result;
    }

    @Override
    public ArrayList<ObjSoftware> getAll() {
        ArrayList<ObjSoftware> result = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(Main.softwarePath)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String nom = jsonObject.getString("nom");
                int any = jsonObject.getInt("any");
                JSONArray jsonLlenguatges = jsonObject.getJSONArray("llenguatges");
                ArrayList<Integer> llenguatges = new ArrayList<>();
                for (int j = 0; j < jsonLlenguatges.length(); j++) {
                    llenguatges.add(jsonLlenguatges.getInt(j));
                }
                ObjSoftware software = new ObjSoftware(id, nom, any, llenguatges);
                result.add(software);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(int id, ObjSoftware software) {
        ArrayList<ObjSoftware> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            llista.set(posicio, software);
            writeList(llista);
        }
    }

    @Override
    public void delete(int id) {
        ArrayList<ObjSoftware> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            llista.remove(posicio);
            writeList(llista);
        }
    }

    @Override
    public void print() {
        ArrayList<ObjSoftware> llista = getAll();
        for (int i = 0; i < llista.size(); i++) {
            System.out.println("    " + llista.get(i));
        }
    }

    @Override
    public void setNom(int id, String nom) {
        ArrayList<ObjSoftware> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjSoftware software = llista.get(posicio);
            software.setNom(nom);
            update(id, software);
        }
    }

    @Override
    public void setAny(int id, int any) {
        ArrayList<ObjSoftware> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjSoftware software = llista.get(posicio);
            software.setAny(any);
            update(id, software);
        }
    }

    public void setLlenguatgesAdd(int id, int idLlenguatge) {
        ArrayList<ObjSoftware> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjSoftware software = llista.get(posicio);
            ArrayList<Integer> llenguatges = software.getLlenguatges();
            llenguatges.add(idLlenguatge);
            software.setLlenguatges(llenguatges);
            update(id, software);
        }
    }

    public void setLlenguatgesDelete(int id, int idLlenguatge) {
        ArrayList<ObjSoftware> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjSoftware software = llista.get(posicio);
            ArrayList<Integer> llenguatges = software.getLlenguatges();
            llenguatges.remove(idLlenguatge);
            software.setLlenguatges(llenguatges);
            update(id, software);
        }
    }
}
package com.project;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DaoEina implements Dao<ObjEina> {

    private void writeList(ArrayList<ObjEina> llista) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (ObjEina eina : llista) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", eina.getId());
                jsonObject.put("nom", eina.getNom());
                jsonObject.put("any", eina.getAny());
                jsonObject.put("llenguatges", eina.getLlenguatges());
                jsonArray.put(jsonObject);
            }

            PrintWriter out = new PrintWriter(Main.einesPath);
            out.write(jsonArray.toString(4));  // 4 es l'espaiat
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int getPosition(int id) {
        int result = -1;
        ArrayList<ObjEina> llista = getAll();
        for (int i = 0; i < llista.size(); i++) {
            ObjEina eina = llista.get(i);
            if (eina.getId() == id) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public void add(ObjEina eina) {
        ArrayList<ObjEina> llista = getAll();
        ObjEina item = get(eina.getId());
        if (item == null) {
            llista.add(eina);
            writeList(llista);
        }
    }

    @Override
    public ObjEina get(int id) {
        ObjEina result = null;
        ArrayList<ObjEina> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            result = llista.get(posicio);
        }
        return result;
    }

    @Override
    public ArrayList<ObjEina> getAll() {
        ArrayList<ObjEina> result = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(Main.einesPath)));
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
                ObjEina eina = new ObjEina(id, nom, any, llenguatges);
                result.add(eina);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(int id, ObjEina eina) {
        ArrayList<ObjEina> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            llista.set(posicio, eina);
            writeList(llista);
        }
    }

    @Override
    public void delete(int id) {
        ArrayList<ObjEina> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            llista.remove(posicio);
            writeList(llista);
        }
    }

    @Override
    public void print() {
        ArrayList<ObjEina> llista = getAll();
        for (int i = 0; i < llista.size(); i++) {
            System.out.println("    " + llista.get(i));
        }
    }

    @Override
    public void setNom(int id, String nom) {
        ArrayList<ObjEina> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjEina eina = llista.get(posicio);
            eina.setNom(nom);
            update(id, eina);
        }
    }

    @Override
    public void setAny(int id, int any) {
        ArrayList<ObjEina> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjEina eina = llista.get(posicio);
            eina.setAny(any);
            update(id, eina);
        }
    }

    public void setLlenguatgesAdd(int id, int idLlenguatge) {
        ArrayList<ObjEina> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjEina eina = llista.get(posicio);
            ArrayList<Integer> llenguatges = eina.getLlenguatges();
            llenguatges.add(idLlenguatge);
            eina.setLlenguatges(llenguatges);
            update(id, eina);
        }
    }

    public void setLlenguatgesDelete(int id, int idLlenguatge) {
        ArrayList<ObjEina> llista = getAll();
        int posicio = getPosition(id);
        if (posicio != -1) {
            ObjEina eina = llista.get(posicio);
            ArrayList<Integer> llenguatges = eina.getLlenguatges();
            llenguatges.remove(idLlenguatge);
            eina.setLlenguatges(llenguatges);
            update(id, eina);
        }
    }
}
package com.example.trabalho3progmobile;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "teste.db";
    private static final String CLIENTE_TABLE_NAME = "cliente";
    private static final String CLIENTE_COL_CPF = "cpf";
    private static final String CLIENTE_COL_NOME = "nome";
    private static final String CLIENTE_COL_SOBRENOME = "sobrenome";
    private static final String CLIENTE_COL_EMAIL = "email";
    private static final String CLIENTE_COL_SENHA = "senha";

    SQLiteDatabase db;

    private static final String CLIENTE_TABLE_CREATE="create table "+CLIENTE_TABLE_NAME+
            "("+CLIENTE_COL_CPF+" integer primary key, "+
            CLIENTE_COL_NOME+" text not null, "+CLIENTE_COL_SOBRENOME+" text not null, " +
            CLIENTE_COL_EMAIL+" text unique not null, "+CLIENTE_COL_SENHA+" text not null);";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CLIENTE_TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+CLIENTE_TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insereCliente(Cliente cliente){
        db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(CLIENTE_COL_CPF, cliente.getCpf());
            values.put(CLIENTE_COL_NOME, cliente.getNome());
            values.put(CLIENTE_COL_SOBRENOME, cliente.getSobrenome());
            values.put(CLIENTE_COL_EMAIL, cliente.getEmail());
            values.put(CLIENTE_COL_SENHA, cliente.getSenha());
            db.insertOrThrow(CLIENTE_TABLE_NAME,null,values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public String buscarSenha(String email){
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                CLIENTE_COL_SENHA, CLIENTE_TABLE_NAME, CLIENTE_COL_EMAIL);
        String senha="não encontrado";
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(email)});
            try {
                if (cursor.moveToFirst()) {
                    senha = cursor.getString(0);
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return senha;
    }

    public ArrayList<Cliente> buscarClientes(){
        String[] coluns={CLIENTE_COL_CPF, CLIENTE_COL_NOME, CLIENTE_COL_SOBRENOME, CLIENTE_COL_EMAIL, CLIENTE_COL_SENHA};
        Cursor cursor = getReadableDatabase().query(CLIENTE_TABLE_NAME,
                coluns,null,null,null,
                null,"upper(nome)",null);
        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
        while(cursor.moveToNext()){
            Cliente c = new Cliente();
            c.setCpf(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setSobrenome(cursor.getString(2));
            c.setEmail(cursor.getString(3));
            c.setSenha(cursor.getString(4));
            listaClientes.add(c);
        }
        return listaClientes;
    }

    public boolean buscarClientePorCpfBoolean(int cpf){
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                CLIENTE_COL_CPF, CLIENTE_TABLE_NAME, CLIENTE_COL_CPF);
        boolean existe = false;
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cpf)});
            try {
                if (cursor.moveToFirst()) {
                    existe = true;
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return existe;
    }

    public boolean buscarClientePorEmailBoolean(String email){
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                CLIENTE_COL_EMAIL, CLIENTE_TABLE_NAME, CLIENTE_COL_EMAIL);
        boolean existe = false;
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(email)});
            try {
                if (cursor.moveToFirst()) {
                    existe = true;
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return existe;
    }


    public Cliente buscarClientePorEmail(String email){
        db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                CLIENTE_TABLE_NAME, CLIENTE_COL_EMAIL);
        db.beginTransaction();

        Cliente cliente = new Cliente();

        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(email)});
            try {
                if (cursor.moveToFirst()) {
                    cliente.setCpf(cursor.getInt(0));
                    cliente.setNome(cursor.getString(1));
                    cliente.setSobrenome(cursor.getString(2));
                    cliente.setEmail(cursor.getString(3));
                    cliente.setSenha(cursor.getString(4));
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return cliente;
    }


    public Cliente buscarClientePorCpf(int cpf){

        db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                CLIENTE_TABLE_NAME, CLIENTE_COL_CPF);
        db.beginTransaction();

        Cliente cliente = new Cliente();

        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cpf)});
            try {
                if (cursor.moveToFirst()) {
                    cliente.setCpf(cursor.getInt(0));
                    cliente.setNome(cursor.getString(1));
                    cliente.setSobrenome(cursor.getString(2));
                    cliente.setEmail(cursor.getString(3));
                    cliente.setSenha(cursor.getString(4));
                    db.setTransactionSuccessful();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return cliente;
    }

    public long atualizarCliente(Cliente novo, Cliente cAtual) {
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLIENTE_COL_NOME, novo.getNome());
        values.put(CLIENTE_COL_SOBRENOME, novo.getSobrenome());
        values.put(CLIENTE_COL_CPF, novo.getCpf());
        values.put(CLIENTE_COL_EMAIL, novo.getEmail());
        values.put(CLIENTE_COL_SENHA, novo.getSenha());
        String[] args = {String.valueOf(cAtual.getCpf())};
        retornoBD=db.update(CLIENTE_TABLE_NAME,values,"cpf=?",args);
        db.close();
        return retornoBD;
    }
}

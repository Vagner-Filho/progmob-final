package com.example.trabalho3progmobile.db;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.trabalho3progmobile.entities.Pedido;
import com.example.trabalho3progmobile.entities.ProdutosPedido;
import com.example.trabalho3progmobile.entities.Produto;
import com.example.trabalho3progmobile.entities.Carrinho;
import com.example.trabalho3progmobile.entities.Cliente;
import com.example.trabalho3progmobile.entities.Fornecedor;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "trabalho3.db";

    private static final String CLIENTE_TABLE_NAME = "cliente";
    private static final String CLIENTE_COL_CPF = "cpf";
    private static final String CLIENTE_COL_NOME = "nome";
    private static final String CLIENTE_COL_SOBRENOME = "sobrenome";
    private static final String CLIENTE_COL_EMAIL = "email";
    private static final String CLIENTE_COL_SENHA = "senha";

    private static final String FORNECEDOR_TABLE_NAME = "fornecedor";
    private static final String FORNECEDOR_COL_CPF = "cpf";
    private static final String FORNECEDOR_COL_NOME_LOJA = "nome_loja";
    private static final String FORNECEDOR_COL_NOME = "nome";
    private static final String FORNECEDOR_COL_SOBRENOME = "sobrenome";
    private static final String FORNECEDOR_COL_LOCALIZACAO = "localizacao";
    private static final String FORNECEDOR_COL_EMAIL = "email";
    private static final String FORNECEDOR_COL_SENHA = "senha";

    private static final String PRODUTO_TABLE_NAME = "produto";
    private static final String PRODUTO_ID = "id";
    private static final String PRODUTO_FOTO = "foto";
    private static final String PRODUTO_NOME = "nome";
    private static final String PRODUTO_DESCRICAO = "descricao";
    private static final String PRODUTO_PRECO = "preco";
    private static final String PRODUTO_UNIDADES_DISPONIVEIS = "unidades_disponiveis";
    private static final String PRODUTO_FORNECEDOR_CPF = "fornecedor_cpf";

    private static final String CARRINHO_TABLE_NAME = "carrinho";
    private static final String CARRINHO_ID = "id";
    private static final String CARRINHO_PRODUTO_ID = "produto_id";
    private static final String CARRINHO_NOME = "nome";
    private static final String CARRINHO_PRECO = "preco";

    private static final String PEDIDO_TABLE_NAME = "pedido";
    private static final String PEDIDO_ID = "id";
    private static final String PEDIDO_CLIENTE_CPF = "cliente_cpf";
    private static final String PEDIDO_FORNECEDOR_CPF = "fornecedor_cpf";
    private static final String PEDIDO_PRECO = "preco";
    private static final String PEDIDO_HORARIO_RETIRADA = "horario_retirada";
    private static final String PEDIDO_DATA_RETIRADA = "data_retirada";
    private static final String PEDIDO_REALIZADO_HORARIO = "horario_predido_realizado";
    private static final String PEDIDO_STATUS = "status";

    private static final String PRODUTOS_PEDIDO_TABLE_NAME = "produtospedido";
    private static final String PRODUTOS_PEDIDO_ID = "id";
    private static final String PRODUTOS_PEDIDO_PRODUTO_ID = "produto_id";

    SQLiteDatabase db;

    private static final String CLIENTE_TABLE_CREATE="create table "+CLIENTE_TABLE_NAME+
            "("+CLIENTE_COL_CPF+" integer primary key, "+
            CLIENTE_COL_NOME+" text not null, "+CLIENTE_COL_SOBRENOME+" text not null, " +
            CLIENTE_COL_EMAIL+" text unique not null, "+CLIENTE_COL_SENHA+" text not null);";

    private static final String FORNECEDOR_TABLE_CREATE="create table "+FORNECEDOR_TABLE_NAME+
            "("+FORNECEDOR_COL_CPF+" integer primary key, "+FORNECEDOR_COL_NOME_LOJA+" text not null, "
            + FORNECEDOR_COL_NOME+" text not null, " +FORNECEDOR_COL_SOBRENOME+" text not null, "
            +FORNECEDOR_COL_LOCALIZACAO+" text not null, " + FORNECEDOR_COL_EMAIL+" text unique not null, "
            +FORNECEDOR_COL_SENHA+" text not null);";

    private static final String PRODUTO_TABLE_CREATE="create table "+PRODUTO_TABLE_NAME+
            "("+PRODUTO_ID+" integer primary key autoincrement, "+ PRODUTO_FOTO+" text, "+
            PRODUTO_NOME+" text not null, " + PRODUTO_DESCRICAO+" text not null, "+
            PRODUTO_PRECO+" real not null, " + PRODUTO_UNIDADES_DISPONIVEIS+" integer not null, "
            + PRODUTO_FORNECEDOR_CPF+" integer not null, " +
            "FOREIGN KEY("+PRODUTO_FORNECEDOR_CPF+") REFERENCES "+FORNECEDOR_TABLE_NAME+"("
            + FORNECEDOR_COL_CPF+") on delete cascade);";

    private static final String CARRINHO_TABLE_CREATE="create table "+CARRINHO_TABLE_NAME+
            "("+CARRINHO_ID+" integer primary key autoincrement, " + CARRINHO_PRODUTO_ID+" integer not null, "
            + CARRINHO_NOME+" text not null, " + CARRINHO_PRECO+" real not null, " +
            "FOREIGN KEY("+CARRINHO_PRODUTO_ID+") REFERENCES "+PRODUTO_TABLE_NAME+"("
            + PRODUTO_ID+") on delete cascade);";

    private static final String PEDIDO_TABLE_CREATE="create table "+PEDIDO_TABLE_NAME+
            "("+PEDIDO_ID+" integer primary key autoincrement, " + PEDIDO_CLIENTE_CPF+" integer not null, "
            + PEDIDO_FORNECEDOR_CPF+" integer not null, " + PEDIDO_PRECO+" real not null, "
            + PEDIDO_HORARIO_RETIRADA+" string not null, " + PEDIDO_DATA_RETIRADA+" string not null, "
            + PEDIDO_REALIZADO_HORARIO+" string not null, " + PEDIDO_STATUS+" integer not null, " +
            "FOREIGN KEY("+PEDIDO_CLIENTE_CPF+") REFERENCES " +CLIENTE_TABLE_NAME+"(" + CLIENTE_COL_CPF+") on delete cascade, " +
            "FOREIGN KEY("+PEDIDO_FORNECEDOR_CPF+") REFERENCES " +FORNECEDOR_TABLE_NAME+"(" + FORNECEDOR_COL_CPF+") on delete cascade);";

    private static final String PRODUTOS_PEDIDO_TABLE_CREATE="create table "+PRODUTOS_PEDIDO_TABLE_NAME+
            "("+PRODUTOS_PEDIDO_ID+" integer not null, " + PRODUTOS_PEDIDO_PRODUTO_ID+" integer not null, " +
            "FOREIGN KEY("+PRODUTOS_PEDIDO_ID+") REFERENCES "+PEDIDO_TABLE_NAME+"(" + PEDIDO_ID+") on delete cascade," +
            "FOREIGN KEY("+PRODUTOS_PEDIDO_PRODUTO_ID+") REFERENCES "+PRODUTO_TABLE_NAME+"(" + PRODUTO_ID+") on delete cascade);";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CLIENTE_TABLE_CREATE);
        db.execSQL(PRODUTO_TABLE_CREATE);
        db.execSQL(FORNECEDOR_TABLE_CREATE);
        db.execSQL(CARRINHO_TABLE_CREATE);
        db.execSQL(PEDIDO_TABLE_CREATE);
        db.execSQL(PRODUTOS_PEDIDO_TABLE_CREATE);

        this.db = db;
/*
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(PRODUTO_FOTO, "R.mipmap.ic_launcher_round");
            values.put(PRODUTO_NOME, "X-burguer");
            values.put(PRODUTO_DESCRICAO, "Delicioso hamburguer de carne bovina com pão e queijo");
            values.put(PRODUTO_PRECO, "15,00");
            values.put(PRODUTO_FORNECEDOR_CPF, "1234567890");
            db.insertOrThrow(PRODUTO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(PRODUTO_FOTO, "R.mipmap.ic_launcher_round");
            values.put(PRODUTO_NOME, "Pão de Queijo");
            values.put(PRODUTO_DESCRICAO, "Delicioso pão de queijo");
            values.put(PRODUTO_PRECO, "3,00");
            values.put(PRODUTO_FORNECEDOR_CPF, "1234567890");
            db.insertOrThrow(PRODUTO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(PRODUTO_FOTO, "R.mipmap.ic_launcher_round");
            values.put(PRODUTO_NOME, "Coca-Cola");
            values.put(PRODUTO_DESCRICAO, "Refrigerante lata de 350ml");
            values.put(PRODUTO_PRECO, "6,00");
            values.put(PRODUTO_FORNECEDOR_CPF, "0987654321");
            db.insertOrThrow(PRODUTO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+CLIENTE_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+PRODUTO_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+CARRINHO_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+FORNECEDOR_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+PEDIDO_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+PRODUTOS_PEDIDO_TABLE_NAME;
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

/*
        String query = "DROP TABLE IF EXISTS "+PRODUTO_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+CARRINHO_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+FORNECEDOR_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+PEDIDO_TABLE_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+PRODUTOS_PEDIDO_TABLE_NAME;
        db.execSQL(query);
        db.execSQL(PRODUTO_TABLE_CREATE);
        db.execSQL(CARRINHO_TABLE_CREATE);
        db.execSQL(FORNECEDOR_TABLE_CREATE);
        db.execSQL(PEDIDO_TABLE_CREATE);
        db.execSQL(PRODUTOS_PEDIDO_TABLE_CREATE);


/*
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(PRODUTO_FOTO, "R.mipmap.ic_launcher_round");
            values.put(PRODUTO_NOME, "X-burguer");
            values.put(PRODUTO_DESCRICAO, "Delicioso hamburguer de carne bovina com pão e queijo");
            values.put(PRODUTO_PRECO, "15,00");
            values.put(PRODUTO_UNIDADES_DISPONIVEIS, "0");
            values.put(PRODUTO_FORNECEDOR_CPF, "1234567890");
            db.insertOrThrow(PRODUTO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(PRODUTO_FOTO, "R.mipmap.ic_launcher_round");
            values.put(PRODUTO_NOME, "X-salada");
            values.put(PRODUTO_DESCRICAO, "Delicioso hamburguer de carne bovina com pão e queijo e salada");
            values.put(PRODUTO_PRECO, "19,00");
            values.put(PRODUTO_UNIDADES_DISPONIVEIS, "5");
            values.put(PRODUTO_FORNECEDOR_CPF, "1234567890");
            db.insertOrThrow(PRODUTO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(PRODUTO_FOTO, "R.mipmap.ic_launcher_round");
            values.put(PRODUTO_NOME, "Pastel");
            values.put(PRODUTO_DESCRICAO, "Delicioso pastel de queijo");
            values.put(PRODUTO_PRECO, "6,00");
            values.put(PRODUTO_UNIDADES_DISPONIVEIS, "20");
            values.put(PRODUTO_FORNECEDOR_CPF, "1234567890");
            db.insertOrThrow(PRODUTO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(PRODUTO_FOTO, "R.mipmap.ic_launcher_round");
            values.put(PRODUTO_NOME, "Pão de Queijo");
            values.put(PRODUTO_DESCRICAO, "Delicioso pão de queijo");
            values.put(PRODUTO_PRECO, "3,00");
            values.put(PRODUTO_UNIDADES_DISPONIVEIS, "15");
            values.put(PRODUTO_FORNECEDOR_CPF, "1234567890");
            db.insertOrThrow(PRODUTO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(PRODUTO_FOTO, "R.mipmap.ic_launcher_round");
            values.put(PRODUTO_NOME, "Coca-Cola");
            values.put(PRODUTO_DESCRICAO, "Refrigerante lata de 350ml");
            values.put(PRODUTO_PRECO, "6,00");
            values.put(PRODUTO_UNIDADES_DISPONIVEIS, "7");
            values.put(PRODUTO_FORNECEDOR_CPF, "0987654321");
            db.insertOrThrow(PRODUTO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
*/
/*
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(FORNECEDOR_COL_CPF, "1234567890");
            values.put(FORNECEDOR_COL_NOME_LOJA, "Só delicias");
            values.put(FORNECEDOR_COL_NOME, "Ronaldinho");
            values.put(FORNECEDOR_COL_SOBRENOME, "Gaúcho");
            values.put(FORNECEDOR_COL_LOCALIZACAO, "00000000");
            values.put(FORNECEDOR_COL_EMAIL, "r@r");
            values.put(FORNECEDOR_COL_SENHA, "123");
            db.insertOrThrow(FORNECEDOR_TABLE_NAME,null,values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }


        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(FORNECEDOR_COL_CPF, "0987654321");
            values.put(FORNECEDOR_COL_NOME_LOJA, "Só a tristeza");
            values.put(FORNECEDOR_COL_NOME, "Girud");
            values.put(FORNECEDOR_COL_SOBRENOME, "dos Santos");
            values.put(FORNECEDOR_COL_LOCALIZACAO, "11111111");
            values.put(FORNECEDOR_COL_EMAIL, "g@g");
            values.put(FORNECEDOR_COL_SENHA, "123");
            db.insertOrThrow(FORNECEDOR_TABLE_NAME,null,values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
*/
    }

    public String buscarSenhaCliente(String email){
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





    public void inserenoCarrinho(Produto produtos, int quantidade, Context context) {
        db = this.getWritableDatabase();

        db.beginTransaction();
        int i = 0;
        try {
            while (i < quantidade){
                ContentValues values = new ContentValues();

                values.put(CARRINHO_PRODUTO_ID, produtos.getId());
                values.put(CARRINHO_NOME, produtos.getNome());
                values.put(CARRINHO_PRECO, produtos.getPreco());
                db.insertOrThrow(CARRINHO_TABLE_NAME, null, values);
                db.setTransactionSuccessful();
                i = i + 1;
            }
            //Toast.makeText(context, String.valueOf(i), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<Carrinho> buscarCarrinho(){
        String[] coluns={CARRINHO_ID, CARRINHO_PRODUTO_ID, CARRINHO_NOME, CARRINHO_PRECO};
        Cursor cursor = getReadableDatabase().query(CARRINHO_TABLE_NAME,
                coluns,null,null,null,
                null,"upper(id)",null);
        ArrayList<Carrinho> listaCarrinho = new ArrayList<Carrinho>();
        while(cursor.moveToNext()){
            Carrinho c = new Carrinho();
            c.setId(cursor.getInt(0));
            c.setId_produto(cursor.getInt(1));
            c.setNome(cursor.getString(2));
            c.setPreco(cursor.getDouble(3));
            listaCarrinho.add(c);
        }
        return listaCarrinho;
    }

    public long excluirDoCarrinho(String id) {
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        retornoBD = db.delete(CARRINHO_TABLE_NAME, "id=?",args);
        return retornoBD;
    }

    public void excluirCarrinho() {
        db = this.getWritableDatabase();

        String query = "DROP TABLE IF EXISTS "+CARRINHO_TABLE_NAME;
        db.execSQL(query);
        db.execSQL(CARRINHO_TABLE_CREATE);
    }




    public void inserePedido(Pedido pedido){
        db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(PEDIDO_CLIENTE_CPF, pedido.getClienteCpf());
            values.put(PEDIDO_FORNECEDOR_CPF, pedido.getFornecedorCpf());
            values.put(PEDIDO_PRECO, pedido.getPreco());
            values.put(PEDIDO_HORARIO_RETIRADA, pedido.getHorarioRetirada());
            values.put(PEDIDO_DATA_RETIRADA, pedido.getDataRetirada());
            values.put(PEDIDO_REALIZADO_HORARIO, pedido.getHorarioRealizado());
            values.put(PEDIDO_STATUS, pedido.getStatus());
            db.insertOrThrow(PEDIDO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

    }

    public Pedido buscarPedidoPorHoraPedidoRelizado(String hora){
        db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                PEDIDO_TABLE_NAME, PEDIDO_REALIZADO_HORARIO);
        db.beginTransaction();

        Pedido pedido = new Pedido();

        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(hora)});
            try {
                if (cursor.moveToFirst()) {
                    pedido.setId(cursor.getInt(0));
                    pedido.setClienteCpf(cursor.getInt(1));
                    pedido.setFornecedorCpf(cursor.getInt(2));
                    pedido.setPreco(cursor.getDouble(3));
                    pedido.setHorarioRetirada(cursor.getString(4));
                    pedido.setDataRetirada(cursor.getString(5));
                    pedido.setHorarioRealizado(cursor.getString(6));
                    pedido.setStatus(cursor.getInt(7));
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
        return pedido;
    }

    public ArrayList<Pedido> buscarPedidos(String cpf){

        String[] coluns={PEDIDO_ID, PEDIDO_CLIENTE_CPF, PEDIDO_FORNECEDOR_CPF, PEDIDO_PRECO,
                PEDIDO_HORARIO_RETIRADA, PEDIDO_DATA_RETIRADA, PEDIDO_REALIZADO_HORARIO, PEDIDO_STATUS};
        Cursor cursor = getReadableDatabase().query(PEDIDO_TABLE_NAME,
                coluns,PEDIDO_CLIENTE_CPF+"="+cpf,null,null,
                null,null,null);
        ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
        while(cursor.moveToNext()){
            Pedido p = new Pedido();
            p.setId(cursor.getInt(0));
            p.setClienteCpf(cursor.getInt(1));
            p.setFornecedorCpf(cursor.getInt(2));
            p.setPreco(cursor.getDouble(3));
            p.setHorarioRetirada(cursor.getString(4));
            p.setDataRetirada(cursor.getString(5));
            p.setHorarioRealizado(cursor.getString(6));
            p.setStatus(cursor.getInt(7));
            listaPedidos.add(p);
        }
        return listaPedidos;
    }

    public long excluirPedido(int id) {
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        retornoBD = db.delete(PEDIDO_TABLE_NAME, "id=?",args);
        retornoBD = db.delete(PRODUTOS_PEDIDO_TABLE_NAME, "id=?",args);
        return retornoBD;
    }

    public ArrayList<Pedido> buscarPedidosDoFornecedor(String cpf){

        String[] coluns={PEDIDO_ID, PEDIDO_CLIENTE_CPF, PEDIDO_FORNECEDOR_CPF, PEDIDO_PRECO,
                PEDIDO_HORARIO_RETIRADA, PEDIDO_DATA_RETIRADA, PEDIDO_REALIZADO_HORARIO, PEDIDO_STATUS};
        Cursor cursor = getReadableDatabase().query(PEDIDO_TABLE_NAME,
                coluns,PEDIDO_FORNECEDOR_CPF+"="+cpf,null,null,
                null,null,null);
        ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
        while(cursor.moveToNext()){
            Pedido p = new Pedido();
            p.setId(cursor.getInt(0));
            p.setClienteCpf(cursor.getInt(1));
            p.setFornecedorCpf(cursor.getInt(2));
            p.setPreco(cursor.getDouble(3));
            p.setHorarioRetirada(cursor.getString(4));
            p.setDataRetirada(cursor.getString(5));
            p.setHorarioRealizado(cursor.getString(6));
            p.setStatus(cursor.getInt(7));
            listaPedidos.add(p);
        }
        return listaPedidos;
    }

    public ArrayList<Pedido> buscarPedidosEmEspera(){

        String[] coluns={PEDIDO_ID, PEDIDO_CLIENTE_CPF, PEDIDO_FORNECEDOR_CPF, PEDIDO_PRECO,
                PEDIDO_HORARIO_RETIRADA, PEDIDO_DATA_RETIRADA, PEDIDO_REALIZADO_HORARIO, PEDIDO_STATUS};
        Cursor cursor = getReadableDatabase().query(PEDIDO_TABLE_NAME,
                coluns,PEDIDO_STATUS+"=0",null,null,
                null,null,null);
        ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
        while(cursor.moveToNext()){
            Pedido p = new Pedido();
            p.setId(cursor.getInt(0));
            p.setClienteCpf(cursor.getInt(1));
            p.setFornecedorCpf(cursor.getInt(2));
            p.setPreco(cursor.getDouble(3));
            p.setHorarioRetirada(cursor.getString(4));
            p.setDataRetirada(cursor.getString(5));
            p.setHorarioRealizado(cursor.getString(6));
            p.setStatus(cursor.getInt(7));
            listaPedidos.add(p);
        }
        return listaPedidos;
    }

    public long aceitarPedido(Pedido pedido) {
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PEDIDO_STATUS, "1");
        String[] args = {String.valueOf(pedido.getId())};
        retornoBD=db.update(PEDIDO_TABLE_NAME,values,"id=?",args);
        this.arrumarQuantidadeProdutos(pedido);
        db.close();
        return retornoBD;
    }

    public long recusarPedido(int id) {
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PEDIDO_STATUS, "-1");
        String[] args = {String.valueOf(id)};
        retornoBD=db.update(PEDIDO_TABLE_NAME,values,"id=?",args);
        db.close();
        return retornoBD;
    }





    public void insereProduto(Produto produto){
        db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(PRODUTO_FOTO, produto.getFoto());
            values.put(PRODUTO_NOME, produto.getNome());
            values.put(PRODUTO_DESCRICAO, produto.getDescricao());
            values.put(PRODUTO_PRECO, produto.getPreco());
            values.put(PRODUTO_UNIDADES_DISPONIVEIS, produto.getQuantidade());
            values.put(PRODUTO_FORNECEDOR_CPF, produto.getFornecedorCpf());
            db.insertOrThrow(PRODUTO_TABLE_NAME,null,values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
        db = this.getWritableDatabase();
    }

    public long excluirProduto(int id) {
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        retornoBD = db.delete(PRODUTO_TABLE_NAME, "id=?",args);
        return retornoBD;
    }

    public ArrayList<Produto> buscarProdutos(String cpf){
        String[] coluns={PRODUTO_ID, PRODUTO_FOTO, PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_PRECO,
                PRODUTO_UNIDADES_DISPONIVEIS, PRODUTO_FORNECEDOR_CPF};
        Cursor cursor = getReadableDatabase().query(PRODUTO_TABLE_NAME,
                coluns,PRODUTO_FORNECEDOR_CPF+"="+cpf,null,null,
                null,"upper(nome)",null);
        ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
        while(cursor.moveToNext()){
            Produto c = new Produto();
            c.setId(cursor.getInt(0));
            c.setFoto(cursor.getString(1));
            c.setNome(cursor.getString(2));
            c.setDescricao(cursor.getString(3));
            c.setPreco(cursor.getDouble(4));
            c.setQuantidade(cursor.getInt(5));
            c.setFornecedorCpf(cursor.getInt(6));
            listaProdutos.add(c);
        }
        return listaProdutos;
    }

    public ArrayList<Produto> buscarProdutosGeral(){
        String[] coluns={PRODUTO_ID, PRODUTO_FOTO, PRODUTO_NOME, PRODUTO_DESCRICAO, PRODUTO_PRECO,
                PRODUTO_UNIDADES_DISPONIVEIS, PRODUTO_FORNECEDOR_CPF};
        Cursor cursor = getReadableDatabase().query(PRODUTO_TABLE_NAME,
                coluns,null,null,null,
                null,"upper(id)",null);
        ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
        while(cursor.moveToNext()){
            Produto c = new Produto();
            c.setId(cursor.getInt(0));
            c.setFoto(cursor.getString(1));
            c.setNome(cursor.getString(2));
            c.setDescricao(cursor.getString(3));
            c.setPreco(cursor.getDouble(4));
            c.setQuantidade(cursor.getInt(5));
            c.setFornecedorCpf(cursor.getInt(6));
            listaProdutos.add(c);
        }
        return listaProdutos;
    }

    public Produto buscarProdutoPorId(String id){
        db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                PRODUTO_TABLE_NAME, PRODUTO_ID);
        db.beginTransaction();

        Produto produto = new Produto();
        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            try {
                if (cursor.moveToFirst()) {
                    produto.setId(cursor.getInt(0));
                    produto.setFoto(cursor.getString(1));
                    produto.setNome(cursor.getString(2));
                    produto.setDescricao(cursor.getString(3));
                    produto.setPreco(cursor.getDouble(4));
                    produto.setQuantidade(cursor.getInt(5));
                    produto.setFornecedorCpf(cursor.getInt(6));
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
        return produto;
    }

    public long atualizarProduto(Produto novo) {
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUTO_FOTO, novo.getFoto());
        values.put(PRODUTO_NOME, novo.getNome());
        values.put(PRODUTO_DESCRICAO, novo.getDescricao());
        values.put(PRODUTO_PRECO, novo.getPreco());
        values.put(PRODUTO_UNIDADES_DISPONIVEIS, novo.getQuantidade());
        String[] args = {String.valueOf(novo.getId())};
        retornoBD=db.update(PRODUTO_TABLE_NAME,values,"id=?",args);
        db.close();
        return retornoBD;
    }

    private void arrumarQuantidadeProdutos(Pedido pedido) {
        ArrayList<ProdutosPedido> produtosPedidos;
        produtosPedidos = this.buscarProdutosPedidoPorId(pedido.getId());
        Produto produto;
        for (ProdutosPedido pp : produtosPedidos) {
            produto = this.buscarProdutoPorId(String.valueOf(pp.getProdutoId()));
            produto.setQuantidade(produto.getQuantidade()-1);
            this.atualizarProduto(produto);
        }
    }

    public boolean existemUnidadesSuficientes(Pedido pedido) {
        ArrayList<Produto> listaProdutos = buscarProdutos(String.valueOf(pedido.getFornecedorCpf()));
        ArrayList<ProdutosPedido> produtosPedidos = this.buscarProdutosPedidoPorId(pedido.getId());
        Produto produto;

        int unidade;
        int x=0;
        for (Produto p : listaProdutos) {
            unidade = 0;
            x++;
            for (ProdutosPedido pp : produtosPedidos) {
                if (p.getId() == pp.getProdutoId()) {
                    unidade++;
                }
            }
            if (p.getQuantidade() - unidade < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean existemUnidadesSuficientesCarrinho(Produto produto, ArrayList<Carrinho> carrinho) {
        int unidade = 0;

        for (Carrinho pp : carrinho) {
            if (produto.getId() == pp.getId_produto()) {
                unidade++;
            }
        }
        if (produto.getQuantidade() - unidade <= 0) {
            return false;
        }
        return true;
    }

    public boolean apenasUmDisponivel(Produto produto, ArrayList<Carrinho> carrinho) {
        int unidade = 0;

        for (Carrinho pp : carrinho) {
            if (produto.getId() == pp.getId_produto()) {
                unidade++;
            }
        }
        if (produto.getQuantidade() - unidade == 1) {
            return true;
        }
        return false;
    }





    public void insereProdutosPedido(ProdutosPedido pp){
        db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(PRODUTOS_PEDIDO_ID, pp.getPedidoId());
            values.put(PRODUTOS_PEDIDO_PRODUTO_ID, pp.getProdutoId());
            db.insertOrThrow(PRODUTOS_PEDIDO_TABLE_NAME, null,values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

    }

    public ArrayList<ProdutosPedido> buscarProdutosPedidoPorId(int id){

        String[] coluns={PRODUTOS_PEDIDO_ID, PRODUTOS_PEDIDO_PRODUTO_ID};
        Cursor cursor = getReadableDatabase().query(PRODUTOS_PEDIDO_TABLE_NAME,
                coluns,PRODUTOS_PEDIDO_ID+"="+String.valueOf(id),null,null,
                null,"upper(id)",null);
        ArrayList<ProdutosPedido> listaProdutosPedidos = new ArrayList<ProdutosPedido>();
        while(cursor.moveToNext()){
            ProdutosPedido pp = new ProdutosPedido();
            pp.setPedidoId(cursor.getInt(0));
            pp.setProdutoId(cursor.getInt(1));
            listaProdutosPedidos.add(pp);
        }
        return listaProdutosPedidos;
    }

    public ArrayList<ProdutosPedido> buscarProdutosPedido(){

        String[] coluns={PRODUTOS_PEDIDO_ID, PRODUTOS_PEDIDO_PRODUTO_ID};
        Cursor cursor = getReadableDatabase().query(PRODUTOS_PEDIDO_TABLE_NAME,
                coluns,null,null,null,
                null,"upper(id)",null);
        ArrayList<ProdutosPedido> listaProdutosPedidos = new ArrayList<ProdutosPedido>();
        while(cursor.moveToNext()){
            ProdutosPedido pp = new ProdutosPedido();
            pp.setPedidoId(cursor.getInt(0));
            pp.setProdutoId(cursor.getInt(1));
            listaProdutosPedidos.add(pp);
        }
        return listaProdutosPedidos;
    }




    public void insereFornecedor(Fornecedor fornecedor){
        db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(FORNECEDOR_COL_CPF, fornecedor.getCpf());
            values.put(FORNECEDOR_COL_NOME_LOJA, fornecedor.getNomeLoja());
            values.put(FORNECEDOR_COL_NOME, fornecedor.getNome());
            values.put(FORNECEDOR_COL_SOBRENOME, fornecedor.getSobrenome());
            values.put(FORNECEDOR_COL_LOCALIZACAO, fornecedor.getLocalizacao());
            values.put(FORNECEDOR_COL_EMAIL, fornecedor.getEmail());
            values.put(FORNECEDOR_COL_SENHA, fornecedor.getSenha());
            db.insertOrThrow(FORNECEDOR_TABLE_NAME,null,values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public String buscarSenhaFornecedor(String email){
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                FORNECEDOR_COL_SENHA, FORNECEDOR_TABLE_NAME, FORNECEDOR_COL_EMAIL);
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

    public ArrayList<Fornecedor> buscarFornecedores(){
        String[] coluns={FORNECEDOR_COL_CPF, FORNECEDOR_COL_NOME_LOJA, FORNECEDOR_COL_NOME, FORNECEDOR_COL_SOBRENOME,
                FORNECEDOR_COL_LOCALIZACAO, FORNECEDOR_COL_EMAIL, FORNECEDOR_COL_SENHA};
        Cursor cursor = getReadableDatabase().query(FORNECEDOR_TABLE_NAME,
                coluns,null,null,null,
                null,"upper(nome_loja)",null);
        ArrayList<Fornecedor> listaFornecedores = new ArrayList<Fornecedor>();
        while(cursor.moveToNext()){
            Fornecedor f = new Fornecedor();
            f.setCpf(cursor.getInt(0));
            f.setNomeLoja(cursor.getString(1));
            f.setNome(cursor.getString(2));
            f.setSobrenome(cursor.getString(3));
            f.setLocalizacao(cursor.getString(4));
            f.setEmail(cursor.getString(5));
            f.setSenha(cursor.getString(6));
            listaFornecedores.add(f);
        }
        return listaFornecedores;
    }

    public boolean buscarFornecedorPorCpfBoolean(int cpf){
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                FORNECEDOR_COL_CPF, FORNECEDOR_TABLE_NAME, FORNECEDOR_COL_CPF);
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

    public boolean buscarFornecedorPorEmailBoolean(String email){
        db = this.getReadableDatabase();
        String query = String.format("SELECT %s FROM %s WHERE %s = ?",
                FORNECEDOR_COL_EMAIL, FORNECEDOR_TABLE_NAME, FORNECEDOR_COL_EMAIL);
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

    public Fornecedor buscarFornecedorPorEmail(String email){
        db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                FORNECEDOR_TABLE_NAME, FORNECEDOR_COL_EMAIL);
        db.beginTransaction();

        Fornecedor fornecedor = new Fornecedor();

        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(email)});
            try {
                if (cursor.moveToFirst()) {
                    fornecedor.setCpf(cursor.getInt(0));
                    fornecedor.setNomeLoja(cursor.getString(1));
                    fornecedor.setNome(cursor.getString(2));
                    fornecedor.setSobrenome(cursor.getString(3));
                    fornecedor.setLocalizacao(cursor.getString(4));
                    fornecedor.setEmail(cursor.getString(5));
                    fornecedor.setSenha(cursor.getString(6));
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
        return fornecedor;
    }

    public Fornecedor buscarFornecedorPorCpf(int cpf){

        db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?",
                FORNECEDOR_TABLE_NAME, FORNECEDOR_COL_CPF);
        db.beginTransaction();

        Fornecedor fornecedor = new Fornecedor();

        try {
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cpf)});
            try {
                if (cursor.moveToFirst()) {
                    fornecedor.setCpf(cursor.getInt(0));
                    fornecedor.setNomeLoja(cursor.getString(1));
                    fornecedor.setNome(cursor.getString(2));
                    fornecedor.setSobrenome(cursor.getString(3));
                    fornecedor.setLocalizacao(cursor.getString(4));
                    fornecedor.setEmail(cursor.getString(5));
                    fornecedor.setSenha(cursor.getString(6));
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
        return fornecedor;
    }

    public long atualizarFornecedor(Fornecedor novo, Fornecedor fAtual) {
        long retornoBD;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FORNECEDOR_COL_NOME, novo.getNome());
        values.put(FORNECEDOR_COL_NOME_LOJA, novo.getNomeLoja());
        values.put(FORNECEDOR_COL_SOBRENOME, novo.getSobrenome());
        values.put(FORNECEDOR_COL_LOCALIZACAO, novo.getLocalizacao());
        values.put(FORNECEDOR_COL_CPF, novo.getCpf());
        values.put(FORNECEDOR_COL_EMAIL, novo.getEmail());
        values.put(FORNECEDOR_COL_SENHA, novo.getSenha());
        String[] args = {String.valueOf(fAtual.getCpf())};
        retornoBD=db.update(FORNECEDOR_TABLE_NAME,values,"cpf=?",args);
        db.close();
        return retornoBD;
    }
}

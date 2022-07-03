package com.example.trabalho3progmobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trabalho3progmobile.activities.PedidosRealizadosActivity;
import com.example.trabalho3progmobile.db.DBHelper;
import com.example.trabalho3progmobile.entities.Fornecedor;
import com.example.trabalho3progmobile.entities.Pedido;
import com.example.trabalho3progmobile.entities.Produto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class EditarProduto extends AppCompatActivity {

    String fotoEmByte;
    DBHelper helper = new DBHelper(this);
    ImageView imagemProduto;
    EditText edtNome;
    EditText edtDesc;
    EditText edtQuant;
    EditText edtPreco;
    Button btnSalvar;
    Produto produto;
    Fornecedor fornecedor;
    Button btnExcluir;
    Button btnTirarFoto;
    Button btnCarregarFoto;
    LinearLayout botoes;
    private static final int PERMISSION_REQUEST_CODE = 200;
    static final int GALLERY = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    byte foto[];
    private static final String IMAGE_DIRECTORY = "/camera2022";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produto);

        Intent intent = getIntent();
        produto = (Produto) intent.getSerializableExtra("infos");
        fornecedor = (Fornecedor) intent.getSerializableExtra("fornecedor");


        edtNome = findViewById(R.id.nome);
        edtDesc = findViewById(R.id.descricao);
        edtQuant = findViewById(R.id.quantidade);
        edtPreco = findViewById(R.id.preco);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);
        btnTirarFoto = findViewById(R.id.tirarFoto);
        btnCarregarFoto = findViewById(R.id.carregarFoto);
        botoes = findViewById(R.id.botoes);
        imagemProduto = findViewById(R.id.imagemProduto);

        if(produto!=null) {
            if (produto.getFoto() != null) {
                byte[] img;
                fotoEmByte = produto.getFoto();
                img = Base64.decode(produto.getFoto(), Base64.DEFAULT);
                Bitmap imgDecodificada = BitmapFactory.decodeByteArray(img, 0, img.length);
                imagemProduto.setImageBitmap(imgDecodificada);
            }
        }

        if (produto != null) {
            edtNome.setText(produto.getNome());
            edtDesc.setText(produto.getDescricao());
            edtQuant.setText(String.valueOf(produto.getQuantidade()));
            edtPreco.setText(String.valueOf(produto.getPreco()));
            btnSalvar.setText("ALTERAR");
        } else {
            btnSalvar.setText("SALVAR");
            btnExcluir.setVisibility(View.INVISIBLE);
            botoes.setOrientation(LinearLayout.VERTICAL);
        }

        btnTirarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        btnCarregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quantidade;
                double preco;

                try {
                    quantidade = Integer.parseInt(String.valueOf(edtQuant.getText()));
                } catch (Exception e) {
                    alert("Quantidade Inválida!");
                    return;
                }

                try {
                    preco = Double.parseDouble(String.valueOf(edtQuant.getText()));
                } catch (Exception e) {
                    alert("Preco Inválido!");
                    return;
                }

                Produto produtoNovo = new Produto();
                produtoNovo.setFoto(fotoEmByte);
                produtoNovo.setNome(String.valueOf(edtNome.getText()));
                produtoNovo.setDescricao(String.valueOf(edtDesc.getText()));
                produtoNovo.setPreco(Double.parseDouble(String.valueOf(edtPreco.getText())));
                produtoNovo.setQuantidade(Integer.parseInt(String.valueOf(edtQuant.getText())));

                if (produto != null) {
                    produtoNovo.setId(Integer.parseInt(String.valueOf(produto.getId())));
                    helper.atualizarProduto(produtoNovo);
                    helper.close();
                    alert("Produto Alterado com Sucesso!");
                } else {
                    produtoNovo.setFornecedorCpf(fornecedor.getCpf());
                    helper.insereProduto(produtoNovo);
                    helper.close();
                    alert("Produto Criado com Sucesso!");
                }

                limpar();
                finish();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produto p = helper.buscarProdutoPorId(String.valueOf(produto.getId()));
                if(p==null) {
                    helper.excluirProduto(produto.getId());
                    helper.close();
                    alert("Produto Excluido com Sucesso!");
                    finish();
                } else
                    alert("Impossível excluir, pois existe um pedido com esse produto!");

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermission()) {
            // Success
        } else {
            requestPermission();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imagemProduto.setImageBitmap(bitmap);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream(
                    bitmap.getWidth() * bitmap.getHeight());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            foto = bytes.toByteArray();
            fotoEmByte = Base64.encodeToString(foto, Base64.DEFAULT);

            Toast.makeText(EditarProduto.this, "Imagem Salva!",
                    Toast.LENGTH_SHORT).show();
        }else if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                            this.getContentResolver(),
                            contentURI);
                    // Salvando a imagem
                    //String path = saveImage(bitmap);
                    //Log.i("TAG","Path: " + path);
                    Toast.makeText(EditarProduto.this, "Image Saved!",
                            Toast.LENGTH_SHORT).show();
                    // Print on screen
                    //Bitmap novoBit = resizeImage(bitmap, 300, 600, "fotoTirada");
                    imagemProduto.setImageBitmap(bitmap);
                    //image.setImageBitmap(bitmap);


                    ByteArrayOutputStream bytes = new ByteArrayOutputStream(
                            bitmap.getWidth() * bitmap.getHeight());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    foto = bytes.toByteArray();
                    fotoEmByte = Base64.encodeToString(foto, Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditarProduto.this, "Failed!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
/*
    public String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(
                bitmap.getWidth() * bitmap.getHeight());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        foto = bytes.toByteArray();

        File directory = new File(Environment.getExternalStorageDirectory()
                + IMAGE_DIRECTORY);
        // Criando o diretório caso ele não exista!
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            File fileImage = new File(directory,
                    Calendar.getInstance().getTimeInMillis() +".jpg");
            fileImage.createNewFile();
            FileOutputStream fo = new FileOutputStream(fileImage);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{fileImage.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved:->" + fileImage.getAbsolutePath());
            return fileImage.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
*/
    /*public static Bitmap resizeImage(Bitmap bitmap, int newWidth, int newHeight, String x) {
        Matrix m = new Matrix();
        m.setScale((float) newWidth / bitmap.getWidth(), (float) newHeight / bitmap.getHeight());
        if(x.equals("fotoTirada")){
            m.postRotate(90, 150, 300);
        }
        Bitmap output = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawBitmap(bitmap, m, new Paint());
        return output;
    }*/

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted",
                            Toast.LENGTH_SHORT).show();
                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied",
                            Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(EditarProduto.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void limpar(){
        edtNome.setText("");
        edtDesc.setText("");
        edtPreco.setText("");
        edtQuant.setText("");
    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}
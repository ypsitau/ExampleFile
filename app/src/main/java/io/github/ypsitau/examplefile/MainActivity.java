package io.github.ypsitau.examplefile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

// Use adb to see a list of created files.
// $ adb shell
// android$ run-as io.github.ypsitau.examplefile
// android$ ls -al files
// android$ ls -al cache
// android$ ls -al app_original
public class MainActivity extends AppCompatActivity {

	Button button_createFiles;
	Button button_listFiles;
	EditText editText_log;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button_createFiles = findViewById(R.id.button_createFiles);
		button_listFiles = findViewById(R.id.button_listFiles);
		editText_log = findViewById(R.id.editText_log);
		final Context context = this;
		printDirInfo();
		button_createFiles.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String str = "Hello World\n";
				for (int i = 0; i < 10; i++) {
					try {
						OutputStream fos = context.openFileOutput(
								String.format("hello%d.txt", i), Context.MODE_PRIVATE);
						fos.write(str.getBytes());
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		button_listFiles.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = context.getFilesDir();
				/*
				File[] fileChildren = file.listFiles();
				for (File fileChild : fileChildren) {
					int bytes = 0;
					try {
						FileInputStream fis = context.openFileInput(fileChild.getName());
						bytes = fis.available();
						fis.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					printf("%-32s %8d\n", fileChild.getName(), bytes);
				}
				*/
				String[] fileNameChildren = file.list();
				for (String fileNameChild : fileNameChildren) {
					int bytes = 0;
					try {
						FileInputStream fis = context.openFileInput(fileNameChild);
						bytes = fis.available();
						fis.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					printf("%-32s %8d\n", fileNameChild, bytes);
				}
			}
		});
	}
	void printDirInfo() {
		final Context context = this;
		{
			File file = context.getFilesDir();
			printf("getFilesDir()\n");
			printf("  getPath: %s\n", file.getPath());
			printf("  getAbsolutePath: %s\n", file.getAbsolutePath());
		}
		printf("----\n");
		{
			File file = context.getCacheDir();
			printf("getCacheDir()\n");
			printf("  getPath: %s\n", file.getPath());
			printf("  getAbsolutePath: %s\n", file.getAbsolutePath());
		}
		printf("----\n");
		{
			File file = context.getDir("original", Context.MODE_PRIVATE);
			printf("getDir(\"original\")\n");
			printf("  getPath: %s\n", file.getPath());
			printf("  getAbsolutePath: %s\n", file.getAbsolutePath());
		}
		printf("----\n");
		{
			File file = context.getExternalFilesDir(null);
			printf("getExternalFilesDir(null)\n");
			printf("  getPath: %s\n", file.getPath());
			printf("  getAbsolutePath: %s\n", file.getAbsolutePath());
		}
	}
	void printf(String format, Object... args) {
		editText_log.append(String.format(format, args));
		editText_log.setSelection(editText_log.getText().length());
	}
}

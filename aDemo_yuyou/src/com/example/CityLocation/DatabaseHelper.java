package com.example.CityLocation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
	// ��û��ʵ����,�ǲ����������๹�����Ĳ���,��������Ϊ��̬
	private static final String name = "city"; // ���ݿ�����
	private static final int version = 1; // ���ݿ�汾

	public DatabaseHelper(Context context)
	{
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.e("info", "create table");
		// �������ݿ��� ������
		// name �ֶ��� ��VARCHAR �ֶ�����
		// ���ݿ�name phone ��������int���͵�imageID
		// ���ݿ���䣺CREATE TABLE ���� (id INTEGER PRIMARY KEY AUTOINCREMENT,name
		// VARCHAR(20),phone VARCHAR(20))

		// ִ�����ݿ����
		db.execSQL(
				"CREATE TABLE IF NOT EXISTS recentcity (id integer primary key autoincrement, name varchar(40), date INTEGER)");
	}

	// �汾����ʱ����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}
}

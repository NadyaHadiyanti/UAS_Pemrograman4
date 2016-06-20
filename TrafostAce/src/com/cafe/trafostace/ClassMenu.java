package com.cafe.trafostace;

public class ClassMenu {
	
	private String id_menu;
    private String nama_menu;
    private String kategori;
    private String status;
    private String harga;

    public void setMenuId (String id_menu)
    {
        this.id_menu = id_menu;
    }

    public String getMenuId()
    {
        return id_menu;
    }

    public void setKategori (String kategori)
    {
        this.kategori = kategori;
    }

    public String getKategori()
    {
        return kategori;
    }
    
    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    
    public void setMenuName (String nama_menu)
    {
        this.nama_menu = nama_menu;
    }

    public String getMenuName()
    {
        return nama_menu;
    }

    public void setHarga (String harga)
    {
        this.harga = harga;
    }

    public String getHarga()
    {
        return harga;
    }

}

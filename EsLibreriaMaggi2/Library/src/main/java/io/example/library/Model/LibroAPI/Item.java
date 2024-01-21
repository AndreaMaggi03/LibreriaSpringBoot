package io.example.library.Model.LibroAPI;

public class Item{
    public String kind;
    public String id;
    public String etag;
    public String selfLink;
    public VolumeInfo volumeInfo;
    public SaleInfo saleInfo;
    public AccessInfo accessInfo;
    public SearchInfo searchInfo;

    @Override
    public String toString() {
        return "Item{" +
                "kind='" + kind + '\'' +
                ", id='" + id + '\'' +
                ", etag='" + etag + '\'' +
                ", selfLink='" + selfLink + '\'' +
                ", volumeInfo=" + volumeInfo +
                ", saleInfo=" + saleInfo +
                ", accessInfo=" + accessInfo +
                ", searchInfo=" + searchInfo +
                '}';
    }
}

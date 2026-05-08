package WzorceProjektowe;

public class KlasaZBuilder {
    int x,y,z;
    String str1,str2;
    private KlasaZBuilder(Builder builder) {
        this.x=builder.x;
        this.y=builder.y;
        this.z=builder.z;
        this.str1=builder.str1;
        this.str2=builder.str2;
    }

    @Override
    public String toString() {
        return "KlasaZBuilder{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", str1='" + str1 + '\'' +
                ", str2='" + str2 + '\'' +
                '}';
    }

    public static class Builder{
        int x,y,z;
        String str1,str2;
        public  Builder(String str1,String str2){
            this.str1=str1;
            this.str2=str2;
        }
        public Builder withX(int x){
            this.x=x;
            return this;
        }
        public Builder withY(int y){
            this.y=y;
            return this;
        }
        public Builder withZ(int z){
            this.z=z;
            return this;
        }
        public KlasaZBuilder build(){
            return new KlasaZBuilder(this);
        }

    }

    static void main() {
        KlasaZBuilder kl=new KlasaZBuilder.Builder("ala", "ma").withX(10).withY(20).withZ(30).build();
        System.out.println(kl);
    }
}

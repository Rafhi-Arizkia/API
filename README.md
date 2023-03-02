# Selamat datang di proyek saya yang menggunakan arsitektur REST API. 
  Proyek ini memungkinkan Anda untuk mengakses sumber daya secara efisien melalui protokol HTTP.
# Tentang REST API yang telah dibuat:

*Fitur-fitur utama dari proyek ini meliputi:

* Berbasis Spring Framework, yang terkenal dengan kinerja dan skalabilitas yang tinggi
* Menggunakan database MySQL server lokal sebagai basis data, yang memberikan fleksibilitas dan kemudahan penggunaan 
  dalam pengembangan proyek
* Menggunakan JPA Repository untuk mengakses data dan manipulasi data
* Model mapper untukk mengkonversi antara objek entity dan DTO
* Dapat diakses dengan mudah melalui berbagai platform dan bahasa pemrograman berkat dukungan standar HTTP

# KENDALA KENDALA SAAT PENGERJAAN PROYEK :

*1.) Kendala saat menggabungkan 2 data di class ProductService yang berelasi ManyToMany(Product dengan Supplier)
   
   dengan contoh codenya sebagai berikut:
   
      public void addSupplier(SupplierEntities supplierEntities, Long productId){
        Optional<ProductEntities> productEntities = productRepo.findById(productId);
        if (productEntities.isPresent()){
            ProductEntities product = productEntities.get();
            Set<SupplierEntities> supplierEntitiesSet = product.getSupplierProduct();
            supplierEntitiesSet.add(supplierEntities);
            product.setSupplierProduct(supplierEntitiesSet);
            supplierEntities.getProductSupplier().add(product);
            saveProduct(product);
        }else {
            throw new RuntimeException("Invalid product Id:" + productId);
        }
      }
      
      error yang ditunjukkan =  "Cannot invoke "java.util.Set.add(Object)" because the return value of 
      "com.web.api.model.entities.SupplierEntities.getProductSupplier()" is null"
      menunjukkan bahwa saat memanggil method add() dari objek Set yang diperoleh dari pemanggilan method 
      getProductSupplier() dari objek supplierEntities menghasilkan nilai null.

      lalu untuk menangani code tersebut kita harus memeriksa SupplierEntites memiliki set agar tidak null dengan  
      menambahkan kondisi null check sebelum menambahkan objek product ke Set. Jika objek bernilai null kita dapat 
      menggunakan new HashSet<>()
      dan menambahkannya ke SupplierEntities menggunakan method setProductSupplier.

      ini kode yang sudah diperbarui:

        public void addSupplier(SupplierEntities supplierEntities, Long productId) {
          Optional<ProductEntities> productEntities = productRepo.findById(productId);
          if (productEntities.isPresent()) {
              ProductEntities product = productEntities.get();
              Set<SupplierEntities> supplierEntitiesSet = product.getSupplierProduct();
              supplierEntitiesSet.add(supplierEntities);
              product.setSupplierProduct(supplierEntitiesSet);
              if (supplierEntities.getProductSupplier() == null) {
                  supplierEntities.setProductSupplier(new HashSet<>());
              }
              supplierEntities.getProductSupplier().add(product);
              saveProduct(product);
          } else {
              throw new RuntimeException("Invalid product Id:" + productId);
          }
      }

*2.) Unsupported Media Type (405)

      Saat Menggunakan anotasi JsonManageReferance pada table Induk dan JsonBackReferance pada kelas anak itu mengalami error saat menampilkan data dan tambah data
      hingga akhirnya dapat diperbaiki dengan menggunakan JsonIgnoreProperties pada setiap relasi ManyToMany
      contoh:
      
      Pada Kelas ProductEntities:
      
      @ManyToMany(cascade = CascadeType.ALL)
      @JoinTable(name = "tb_product_supplier",
              joinColumns = @JoinColumn(name = "product_id"),
              inverseJoinColumns = @JoinColumn(name = "supplier_id"))
      @JsonIgnoreProperties("productSupplier")
      private Set<SupplierEntities> supplierProduct;
      
      Pada kelas SupplierEntities:
      
      @ManyToMany(mappedBy = "supplierProduct")
      @JsonIgnoreProperties("SupplierProduct")
      private Set<ProductEntities> productSupplier;

*3.) Tidak dapat menggunakan swagger Karena saya menggunakan Spring Boot versi 3.0.2
      dan sebagai gantinya dapat menggunakan documentasi API dengan springdoc-api (https://springdoc.org/v2/)
      

=======================================================
Saya berharap proyek ini dapat membantu Anda dalam mempelajari atau belajar bersama dalam membangun REST API. 
Saya sangat terbuka terhadap masukan dan saran dari kalian untuk terus memperbaiki dan meningkatkan proyek ini. 
Terima kasih untuk supportnya!

package by.bsuir.mobile.lab2.entities

class SSD {
    private var id:String = ""
    private var imagePath:String = ""
    private var memory:Int = 0
    private var model:String = ""
    private var price:Int = 0
    private var readSpeed:Int = 0
    private var writeSpeed:Int = 0

    constructor()


    fun getId() : String{
        return id
    }
    fun getImagePath() : String{
        return imagePath
    }
    fun getMemory() : Int{
        return memory
    }
    fun getModel() : String{
        return model
    }
    fun getPrice(): Int {
        return price
    }
    fun getReadSpeed() : Int {
        return readSpeed
    }
    fun getWriteSpeed() : Int{
        return writeSpeed
    }

    fun setId(id:String){
        this.id = id
    }

    fun setImagePath(path:String){
        this.imagePath=path
    }
    fun setMemory(memory:Int){
        this.memory = memory
    }
    fun setModel(model:String){
        this.model = model
    }
    fun setPrice(price:Int){
        this.price = price
    }
    fun setReadSpeed(speed:Int){
        this.readSpeed = speed
    }
    fun setWriteSpeed(speed:Int){
        this.writeSpeed = speed
    }
}
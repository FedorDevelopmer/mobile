//
//  SSDs.swift
//  lab1
//
//  Created by fedor on 3.03.24.
//

import Foundation
import Firebase
import FirebaseFirestore


class SSD: Identifiable{
    private static let db = Firestore.firestore()
    var id = UUID()
    private var model:String = ""
    private var memory:Int = 0
    private var writeSpeed:Int = 0
    private var readSpeed:Int = 0
    private var price:Int = 0
    
    public func getModel()->String{
        return self.model
    }
    public func getMemory()->Int{
        return self.memory
    }
    public func getWriteSpeed()->Int{
        return self.writeSpeed
    }
    public func getReadSpeed()->Int{
        return self.readSpeed
    }
    public func getPrice()->Int{
        return self.price
    }
    public func getId()->UUID{
        return self.id
    }
    
    public func setModel(model:String)->Void{
        self.model = model
    }
    public func setMemory(memory:Int)->Void{
        self.memory = memory
    }
    public func setWriteSpeed(writeSpeed:Int)->Void{
        self.writeSpeed = writeSpeed
    }
    public func setReadSpeed(readSpeed:Int)->Void{
        self.readSpeed = readSpeed
    }
    public func setPrice(price:Int)->Void{
        self.price = price
    }
    
    public static func getItemsFromDB(completion:@escaping(Array<SSD>)->Void){
        let collectionRef = db.collection("ssd")
        var array = Array<SSD>()
        collectionRef.getDocuments{(snapshot,error) in
            if let error = error {
                return
            }
            guard let snapshot = snapshot else {
                return
            }
            for document in snapshot.documents{
                let ssd = SSD()
                let data = document.data()
                if let value = data["model"] as? String{
                    print("Model: \(value)")
                    ssd.setModel(model: value)
                }
                if let value = data["memory"] as? Int {
                    print("Memory: \(value)")
                    ssd.setMemory(memory: value)
                }
                if let value = data["writeSpeed"] as? Int {
                    print("Write Speed: \(value)")
                    ssd.setWriteSpeed(writeSpeed:value)
                }
                if let value = data["readSpeed"] as? Int{
                    print("Read Speed: \(value)")
                    ssd.setReadSpeed(readSpeed:value)
                }
                if let value = data["price"] as? Int{
                    print("Price: \(value)")
                    ssd.setPrice(price:value)
                }
                array.append(ssd)
                
            }
            completion(array)
        }
    }
}

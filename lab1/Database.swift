//
//  Database.swift
//  lab1
//
//  Created by fedor on 2.03.24.
//

import Foundation
import Firebase
import FirebaseFirestore

struct Dat{
    func addTestData(){
        let db = Firestore.firestore()
        do{
        db.collection("test").document("TD").setData([
            "name":"fedor",
            "lastname":"saprankov",
            "role":"admin"
        ])
            print("Operation successed")
        }catch{
            print("Error: \(error)")
        }
    }
}


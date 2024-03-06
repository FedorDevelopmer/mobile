//
//  FeaturedItemsView.swift
//  lab1
//
//  Created by fedor on 6.03.24.
//

import Foundation
import Firebase
import FirebaseFirestore
import SwiftUI

struct FeaturedItemsView : View {
    @EnvironmentObject var appPage: PageState
    @EnvironmentObject var auth: AuthState
    @State private var ssdArray : Array<SSD> = Array<SSD>()
    
    var body: some View {
        Text("Header").padding().onAppear{
            getFeaturedFromDB()
        }
        List(ssdArray,id:\.self){ item in
            Item(item:item,page:appPage)
        }
        .overlay(HStack{
            Spacer()
            Button(action: {appPage.page = PageEnum.PROFILE}, label: {
                VStack{
                    Image(systemName: "person.circle")
                        .resizable()
                        .scaledToFit()
                    Text("Profile")
                        .font(.system(size:14))
                }
            }).padding(EdgeInsets(top: 0, leading: 5, bottom: 0, trailing: 5))
            .frame(height:50)
            Spacer()
            Button(action: {appPage.page = PageEnum.ITEMS}, label: {
                VStack{
                    Image(systemName: "list.bullet")
                        .resizable()
                        .scaledToFit()
                    Text("Items")
                        .font(.system(size:14))
                }
                    
            }).padding(EdgeInsets(top: 0, leading: 5, bottom: 0, trailing: 5))
            .frame(height:50)
            Spacer()
            Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                VStack{
                    Image(systemName: "star.circle")
                        .resizable()
                        .scaledToFit()
                    Text("Featured")
                        .font(.system(size:14))
                }
                    
            }).padding(EdgeInsets(top: 0, leading: 5, bottom: 0, trailing: 5))
            .frame(height:50)
            Spacer()
        }, alignment: .bottom)
    }
    
    func getFeaturedFromDB(){
        let db = Firestore.firestore()
        var userEmail:String = ""
        var array:Array<SSD> = Array<SSD>()
        if let currentUser = Auth.auth().currentUser {
            userEmail = currentUser.email ?? ""
        } else {
            print("Unauthorized user")
            return
        }
        db.collection(userEmail).getDocuments{(snapshot,error) in
            if let error = error {
                return
            }
            guard let snapshot = snapshot else {
                return
            }
            for document in snapshot.documents{
                let data = document.data()
                if let doc = data["ssdDocument"] as? String{
                    self.getFeaturedItemFromDB(featuredSsdDocument: doc)
                }
            }
        }
    }
    
    private func getFeaturedItemFromDB(featuredSsdDocument:String){
        var isExisting: Bool = false
        let db = Firestore.firestore()
        var userEmail:String = ""
        if let currentUser = Auth.auth().currentUser {
            userEmail = currentUser.email ?? ""
        } else {
            print("Unauthorized user")
            return
        }
        let docRef = db.collection("ssd").document(featuredSsdDocument)
        var ssd:SSD = SSD()
        let document = docRef.getDocument(source: .default,completion: {(snapshot,error) in
            if let error = error {
                return
            }
            guard let doc = snapshot else {
                return
            }
            if let data = doc.data(){
                ssd = SSD()
                ssd.setId(id:doc.documentID)
                if let value = data["model"] as? String{
                    ssd.setModel(model: value)
                }
                if let value = data["memory"] as? Int {
                    ssd.setMemory(memory: value)
                }
                if let value = data["writeSpeed"] as? Int {
                    ssd.setWriteSpeed(writeSpeed:value)
                }
                if let value = data["readSpeed"] as? Int{
                    ssd.setReadSpeed(readSpeed:value)
                }
                if let value = data["price"] as? Int{
                    ssd.setPrice(price:value)
                }
                ssdArray.append(ssd)
            }
        })
    }
}

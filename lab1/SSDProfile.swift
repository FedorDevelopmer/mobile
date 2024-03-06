//
//  SSDProfile.swift
//  lab1
//
//  Created by fedor on 5.03.24.
//

import Foundation
import Firebase
import FirebaseStorage
import SwiftUI

struct SSDProfile : View {
    @EnvironmentObject var appPage: PageState
    @EnvironmentObject var auth: AuthState
    @State var arrayOfImages = Array<UIImage>()
    @State private var selection = 0
    
    var body : some View {
        Text("SSD Description").onAppear{
            loadImageForSSD()
        }
        VStack{
            TabView(selection:$selection){
                    if(arrayOfImages.count == 0){
                        Image(systemName: "xmark.icloud.fill")
                        .resizable()
                        .scaledToFit()
                    } else {
                       
                        ForEach(arrayOfImages,id:\.self){ item in
                            if let curr = item {
                                Image(uiImage: curr)
                                    .resizable()
                                    .scaledToFit()
                            }
                        }
                    }
                }
                .id(UUID())
                .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
                .frame(height:200,alignment: .center)
        }
        Button(action: {
            appPage.page = PageEnum.ITEMS
        }, label: {
            Text("To items page")
        })
    }
    
    func loadImageForSSD(){
        let storageRef = Storage.storage().reference().child("images/ssd1/")
        storageRef.listAll { (result,error) in
            if let error = error {
                print("Error of receiving file: \(error)")
                return
            }
            for item in result.items {
                item.getData(maxSize: INT64_MAX, completion: {(data,error) in
                    if let error = error {
                        print("Error of receiving file: \(error)")
                        return
                    }
                    guard let imageData = data else{
                        print("Error of receiving file data")
                        return
                    }
                    if let image = UIImage(data: imageData){
                        arrayOfImages.append(image)
                        print("Image succesfully loaded")
                    }
                })
            }
        }
    }
}

struct AdditionalView: View {
    var body: some View{
        Text("Stub")
    }
}

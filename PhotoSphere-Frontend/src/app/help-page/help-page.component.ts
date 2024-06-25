import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-help-page',
  templateUrl: './help-page.component.html',
  standalone: true,
  imports: [
    NavBarComponent,
    NgForOf,
    NgIf
  ],
  styleUrls: ['./help-page.component.css']
})
export class HelpPageComponent {
  faqs: { question: string, answer: string, showAnswer: boolean }[] = [
    { question: 'How do I edit my user profile?', answer: 'To edit your user profile, click on the "Account" button in the navigation bar, then click on the "Edit profile" button in your profile page.', showAnswer: false },
    { question: 'How do I upload a photo?', answer: 'To upload a photo, click on the "Create" button in the navigation bar and follow form.', showAnswer: false },
    { question: 'How do I search for photos?', answer: 'To search for photos, use the search bar at the top of the page in the navigation bar and type in photo tag which you are looking for.', showAnswer: false },
    { question: 'How do I log out?', answer: 'To log out, click on the "Log out" button in the navigation bar.', showAnswer: false },
    { question: 'How do I view my profile?', answer: 'To view your profile, click on the "Account" button and navigate to your profile page.', showAnswer: false },
    { question: 'How do I delete a photo?', answer: 'To delete your photo, click on the photo you would like to delete from your profile, then click "Edit photo" button, and choose "Delete post" option.', showAnswer: false },
    { question: 'I do not want everyone to see my photos, what to do?', answer: 'While creating your new post, you have the possibility to choose "Private" option for your photo. If you choose it, you will be the only person to see this post in the system', showAnswer: false },
    { question: 'I accidentally chose public option when creating my post, but I want it to be private, can I change it?', answer: 'Yes! Just click on your post, go to "Edit photo" and change from option public to private. This will ensure you are the only one to see your post.', showAnswer: false },
    { question: 'Can I add new categories to the system if I did not find any category suitable for my post?', answer: 'Yes! Just type in the new category name in the Category field while creating new post. It will automatically create new category in the system.', showAnswer: false },
    { question: 'Can I add more then one category to my post?', answer: 'No. One post can have only one category. But, one post can have many tags, feel free to add as many as you wish!', showAnswer: false },
    { question: 'Can I add more then one tag to my post?', answer: 'Yes, you can add as many tags as you want to your post. Just make sure tags are coma separated.', showAnswer: false },
    { question: 'Can I edit the photo category after I add a post?', answer: 'No, you cannot modify photo category or photo tags after uploading a photo to the system.', showAnswer: false },

  ];

  toggleAnswer(faq: { showAnswer: boolean }): void {
    faq.showAnswer = !faq.showAnswer;
  }
}

const menuIntroContainer = document.getElementsByClassName('menu-intro-container')[0];

const typewriteTexts = [
    "Confused what to watch? ", "Mind numb? ", "Bored and genreless? ", "Ready to explore? ",
    "Search, filter, and find. Just browse away...", "Bon Voyage "
];

const typewriteEmojis = [
    "🤔", "😶", "🥱", "🌟", "", "🍿"
];

class TxtType {
    constructor(el, texts, emojis, period) {
        this.texts = texts;
        this.emojis = emojis;
        this.el = el;
        this.loopNum = 0;
        this.period = parseInt(period, 10) || 2000;
        this.currentText = '';
        this.isDeleting = false;
        this.tick();
    }

    tick() {
        const i = this.loopNum % this.texts.length;
        const currentText = this.texts[i];
        const currentEmoji = this.emojis[i];

        menuIntroContainer.style.gridTemplateColumns = `${(this.currentText.length/2)}px auto`;

        if (this.isDeleting) {
            this.currentText = currentText.substring(0, this.currentText.length - 1);
        } else {
            this.currentText = currentText.substring(0, this.currentText.length + 1);
        }

        this.el.querySelector('.wrap').textContent = this.currentText;
        document.querySelector('.emoji-wrap').textContent = currentEmoji;

        const that = this;
        let delta = 35.6; // Smaller value for smoother animation

        if (this.isDeleting) {
            delta /= 1.5;
        }

        if (!this.isDeleting && this.currentText === currentText) {
            delta = this.period;
            this.isDeleting = true;
        } else if (this.isDeleting && this.currentText === '') {
            this.isDeleting = false;
            this.loopNum++;
            delta = 500; // Adjust for pause at the end of a sentence
        }

        // Check if the loop is at the end and remove the 'intro' element
        if (this.loopNum === this.texts.length) {
            const introElement = document.querySelector('.intro');
            if (introElement) {
                introElement.remove();
                // reset the grid-template-columns for inserting menu component there
                menuIntroContainer.style.gridTemplateColumns = "0% auto";
                // Create and insert a new menu bar component
                fadeInTopBar();
            }
        }

        setTimeout(function () {
            that.tick();
        }, delta);
    }
}

const period = 1300; // 1.3 seconds
document.addEventListener("DOMContentLoaded", function () {
    var elements = document.getElementsByClassName('intro-typewrite');
    for (var i = 0; i < elements.length; i++) {
        new TxtType(elements[i], typewriteTexts, typewriteEmojis, period);
    }
});

const topbar = document.getElementsByClassName("topbar")[0];
const divider2 = document.getElementsByClassName("divider2")[0];

function fadeInTopBar() {
    // move the divider2's content towards right
    divider2.style.justifyContent = 'flex-end';
    // appear with transition
    topbar.style.display = 'flex';
    topbar.style.transition = 'opacity 0.03s ease-in-out';
}
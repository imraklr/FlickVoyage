window.onload = function() {
    // The page has fully loaded, so hide the loading overlay
    hidePageProgressIndicator();
}

window.addEventListener('contextmenu', function (e) {
    e.preventDefault();
});


function showPageProgressIndicator() {
    var loadingOverlay = document.querySelector('.page-progress-indicator');
    loadingOverlay.style.display = 'inline';
}
function hidePageProgressIndicator() {
    // The page's content has fully loaded, so hide the loading overlay
    var loadingOverlay = document.querySelector('.page-progress-indicator');
    loadingOverlay.style.display = 'none';
}

let square1 = null;
let square2 = null;

function getPiece(square) {
    if (square1 == null) {
        square1 = square;
    } else {
        square2 = square;
    }

    if (square1 != null && square2 != null) {
        alert(square1.querySelector('img').src +'\n' + square2.querySelector('img').src)
        const piesaSquare1 = square1.getAttribute('data-piesa');
        const imageUrlSquare1 = square1.querySelector('img').src;

        square2.setAttribute('data-piesa', piesaSquare1);
        square2.querySelector('img').src = imageUrlSquare1;

        square1.setAttribute('data-piesa', 'none');
        square1.querySelector('img').src = "";

        square1 = null;
        square2 = null;
    }
}


function getInfoCasa(id, piesa) {
    const message = `Casa ${id} conține piesa: ${piesa}`;
    alert(message);
}

// Grammar for ABC music notation 

// A subset of abc 2.1 in BNF format

abc ::= abc_header abc_body;

//
// Header

// ignore space_or_tab between terminals in the header
@skip space_or_tab{
    abc_header ::= field_number comment* field_title other_fields* field_key;
    field_number ::= "X:" digit end_of_line;
    field_title ::= "T:" text end_of_line;
    other_fields ::= field_composer | field_default_length | field_meter | field_tempo | field_voice | comment;
    field_composer ::= "C:" text end_of_line;
    field_default_length ::= "L:" note_length_strict end_of_line;
    field_meter ::= "M:" meter end_of_line;
    field_tempo ::= "Q:" tempo end_of_line;
    field_voice ::= "V:" text end_of_line;
    field_key ::= "K:" key end_of_line;
  
    key ::= keynote mode_minor?;
    keynote ::= basenote key_accidental?;
    key_accidental ::= "#" | "b";
    mode_minor ::= "m";

    meter ::= "C" | "C|" | meter_fraction;
    meter_fraction ::= digit+ "/" digit+;

    tempo ::= meter_fraction "=" digit+;
}

//
// Body

// spaces and tabs have explicit meaning in the body, don't automatically ignore them

abc_body ::= abc_line+;
@skip space_or_tab{
abc_line ::= ((start_repeat | first_repeat | second_repeat | barline)? measure (finish_repeat | barline))+ end_of_line (lyric)?  | measure end_of_line | field_voice /*middle_of_body_field*/ | comment;
measure ::= (note_element | rest_element | tuplet_element)+;

//element ::= note_element | rest_element | tuplet_element | barline | nth_repeat | space_or_tab;
// notes
note_element ::= note | chord;

note ::= pitch note_length?;
pitch ::= accidental? basenote octave?;
octave ::= "'"+ | ","+;
note_length ::= (digit+)? ("/" (digit+)?)?;
note_length_strict ::= digit+ "/" digit+;
}
// "^" is sharp, "_" is flat, and "=" is neutral
accidental ::= "^" | "^^" | "_" | "__" | "=";

basenote ::= "C" | "D" | "E" | "F" | "G" | "A" | "B"
        | "c" | "d" | "e" | "f" | "g" | "a" | "b";

// rests
rest_element ::= "z" note_length?;

// tuplets
tuplet_element ::= tuplet_spec note_element+;
tuplet_spec ::= "(" digit ;

// chords
chord ::= "[" note+ "]";
major_barline ::= "||" | "[|" | "|]";
normal_barline ::= "|";
barline ::= major_barline | normal_barline; /*| ":|" | "|:" ;*/
/* repeat ::= ":|" | "|:" | nth_repeat; replaced above*/
start_repeat ::= "|:";
finish_repeat ::= ":|";
first_repeat ::= "[1";
second_repeat ::= "[2";

// A voice field might reappear in the middle of a piece
// to indicate the change of a voice
//middle_of_body_field ::= field_voice;

lyric ::= "w:" lyrical_element* end_of_line;
lyrical_element ::= " " | "-" | "_" | "*" | "~" | backslash_hyphen | barline | lyric_text;
// lyric_text should be defined appropriately
lyric_text ::= [^-_*~\s|\\]*;

backslash_hyphen ::= "\\" "-";
// backslash immediately followed by hyphen

//
// General

comment ::= space_or_tab* "%" comment_text newline;
// comment_text should be defined appropriately
comment_text ::= (text | " ");
end_of_line ::= comment | newline;

text ::= ([^"\n"]+ | " ")*;
digit ::= [0-9]+;
newline ::= "\n" | "\r" "\n"?;
space_or_tab ::= " " | "\t";
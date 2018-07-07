# Finite Context Models and Automatic Generation of Text
Collection of statistical information about texts using finite context models, as well as, generation of automatic text.

## How To Run
Execute the application with the following command:
```
filename order alpha size
```
Arguments:
- **filename** - path of file to read;
- **order** - order of Markov's Model;
- **alpha** - value of alpha (between 0.001 and 1);
- **size** - length of generated text;

Alpha and size are optinal arguments. By omission, the value of alpha is 1 and size is equal or greater to the value of the Markov's order.

Examples:
```
“I Have a Dream.txt” 3
```

```
“I Have a Dream.txt” 3 0.2 100
```


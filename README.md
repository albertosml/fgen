# fgen

## Description

**fgen** is an application whose goal is to allow users to generate
invoices emitted between registered customers on the system.

The customer can be a particular person or a company. It is identified
on the system by a unique code. Then, it will be defined by some
required data like the name and the TIN (Tax Identification Number).
Besides, it will have optional data such as the address, the city, the
province, the ZIP code and the IBAN.

Once the customer is registered on the system, the application will
allow us to modify all its data (except the code). Furthermore, it
also offers the possibility of removing the user, and even restoring it.

The invoice is composed by the sender and the recipient customers, the
list of goods purchased or services provided, which will be called as
“items”, and a set of subtotals which can alter the invoice total.

Each “item” includes the quantity, the description and the price. The
subtotal can be a discount or a tax, which will be defined by a name and
a percentage. Besides, the invoice will also contain additional metadata
like generation datetime.

Respecting its characteristics, an invoice can have the same customer as
recipient and sender at the same time. Then, its generation is based on
an invoice template which can be configured. Finally, once the invoice
has been created, it cannot be edited or removed.

The invoice template indicates how each invoice attribute must be filled
on the generated invoice. It is composed by the mapping between each field
position in the spreadsheet file and its expression, which establishes the
content to show on that field. Besides, it stores the spreadsheet file to
use to fill the invoice.

Finally, the generated invoice can be saved in your local computer in a
spreadsheet format like Excel (XLSX) or PDF format.

## Architecture & Technologies

In the first phase of the project, a monolithic architecture which will be
composed by the next three layers:

- **Presentation**: It will responsible for showing the system
information to the user. In other words, it will contain all the UI.
- **Application**: It will contain all the business logic with all
the use cases.
- **Persistence**: This layer will be responsible for accessing to the
data. It will interact with the database.

Then, each entity will be distinguished in a separate folder, so it is
independent from the others. This will facilitate a future transition
to isolated microservices. Note that common resources for all entities
will be set in a shared folder.

This architecture has been chosen because it will facilitate the
development and the product testing during this first project phase, as
it is preferred to set more focus on the logic at this moment. Then,
improvements could be made based on this first version.

Respect to the technology stack, Java will be used as the programming
language because with the help of the Netbeans IDE, it will accelerate
the UI development, so we can set a better focus to the logic and the
system functionality, as mentioned before. The chosen programming
language also has an easy learning curve and its generated applications
can be executed easily on any operating system after installing Java.
Besides, the Mongo database has been chosen due to its installation
facility on any place (locally and cloud), scalability and flexibility
on the data structure. Keep in mind that there are some entities like
the customer which will have optional data. Finally, the conversion from
a spreadsheet format to PDF will be done thanks to the [Gembox](https://www.gemboxsoftware.com/)
library, which offers a free license to process 150 rows per sheet and a
maximum of 5 sheets per file.

## Entities & Use cases

1. **Customer**: It acts as an entity which emits or receives an invoice.
The customer can be a particular person or a company.

    Use cases:

    - [CS-1](https://github.com/albertosml/fgen/issues/1): Register a customer
    - [CS-2](https://github.com/albertosml/fgen/issues/10): List customers
    - [CS-3](https://github.com/albertosml/fgen/issues/11): Show customer
    - [CS-4](https://github.com/albertosml/fgen/issues/12): Remove customer
    - [CS-5](https://github.com/albertosml/fgen/issues/13): Restore customer
    - [CS-6](https://github.com/albertosml/fgen/issues/14): Update customer

2. **Invoice**: It is a list of all goods purchased or services provided
from one customer to another.

    Use cases:

    - [INV-1](https://github.com/albertosml/fgen/issues/16): Generate invoice
    - [INV-2](https://github.com/albertosml/fgen/issues/17): List all generated invoices
    - [INV-3](https://github.com/albertosml/fgen/issues/18): List all generated invoices by customer
    - [INV-4](https://github.com/albertosml/fgen/issues/19): Save invoice
    - [INV-5](https://github.com/albertosml/fgen/issues/20): Retrieve invoice

3. **Invoice item**: It represents a good purchased or service provided, which
is set on a specific invoice.

    Use cases:

    - [INVIT-1](https://github.com/albertosml/fgen/issues/21): Add an invoice item
    - [INVIT-2](https://github.com/albertosml/fgen/issues/22): Edit an invoice item
    - [INVIT-3](https://github.com/albertosml/fgen/issues/23): Remove an invoice item    

4. **Template**: It establishes the pattern to generate the invoices based on a
spreadsheet file.

    Use cases:

    - [TEM-1](https://github.com/albertosml/fgen/issues/24): Register template
    - [TEM-2](https://github.com/albertosml/fgen/issues/25): List templates
    - [TEM-3](https://github.com/albertosml/fgen/issues/26): Show template
    - [TEM-4](https://github.com/albertosml/fgen/issues/27): Edit template
    - [TEM-5](https://github.com/albertosml/fgen/issues/28): Remove template
    - [TEM-6](https://github.com/albertosml/fgen/issues/29): Restore template

5. **Template field**: It sets up the mapping between a field from a template
spreadsheet file field and the content to render defined by an expression. Note
that the field will be only associated with one template.

    Use cases:

    - [TEMFLD-1](https://github.com/albertosml/fgen/issues/30): Add template field
    - [TEMFLD-2](https://github.com/albertosml/fgen/issues/31): Edit template field
    - [TEMFLD-3](https://github.com/albertosml/fgen/issues/32): Remove template field

6. **Subtotal**: It represents a discount or a tax, which can alter an invoice
total. That subtotal can be associated with any template.

    Use cases:

    - [SUBT-1](https://github.com/albertosml/fgen/issues/33): Add subtotal
    - [SUBT-2](https://github.com/albertosml/fgen/issues/34): List subtotals
    - [SUBT-3](https://github.com/albertosml/fgen/issues/35): Edit subtotal
    - [SUBT-4](https://github.com/albertosml/fgen/issues/36): Remove subtotal
    - [SUBT-5](https://github.com/albertosml/fgen/issues/37): Restore subtotal
    - [SUBT-6](https://github.com/albertosml/fgen/issues/38): Calculate subtotal

7. **Variable**: It establishes the mapping between an entity attribute with a
descriptive name to add it in an expression. Variables are global, so they can
be added in whatever expression.

    Use cases:

    - [VAR-1](https://github.com/albertosml/fgen/issues/39): Add variable
    - [VAR-2](https://github.com/albertosml/fgen/issues/41): List variables
    - [VAR-3](https://github.com/albertosml/fgen/issues/40): Edit variable
    - [VAR-4](https://github.com/albertosml/fgen/issues/42): Remove variable
    - [VAR-5](https://github.com/albertosml/fgen/issues/43): Restore variable

## License

A GNU General Public License v3.0 license (GNU GPLv3) has been chosen
because it lets people to do whatever they want with the project, except
distributing closed source versions.

Source: [https://choosealicense.com/](https://choosealicense.com/)